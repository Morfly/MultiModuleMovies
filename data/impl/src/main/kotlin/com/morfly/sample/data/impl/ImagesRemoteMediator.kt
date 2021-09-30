/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morfly.sample.data.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.morfly.sample.common.domain.ImagesRepository
import com.morfly.sample.data.impl.mapping.DataMapper
import com.morfly.sample.data.impl.network.PixabayApi
import com.morfly.sample.data.impl.storage.AppDatabase
import com.morfly.sample.data.impl.storage.entity.ImageRemoteKeys
import com.morfly.sample.data.impl.storage.entity.StoredImage
import retrofit2.HttpException
import java.io.IOException


const val FIRST_PAGE = 1


@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator(
    private val query: String,
    private val networkService: PixabayApi,
    private val database: AppDatabase,
    private val mapper: DataMapper
) : RemoteMediator<Int, StoredImage>() {

    private val imagesDao = database.imagesDao()
    private val imagesRemoteKeysDao = database.imageRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, StoredImage>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                REFRESH -> FIRST_PAGE
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItemRemoteKeys = findRemoteKeysForLastItem(state)

                    if (lastItemRemoteKeys?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    lastItemRemoteKeys.nextKey
                }
            }

            val images = networkService.images(query, loadKey, ImagesRepository.PAGE_SIZE)
                .hits
                .map { mapper.networkToStorage(it, query) }

            val endOfPaginationReached = images.isEmpty()

            database.withTransaction {
                if (loadType == REFRESH) {
                    imagesDao.deleteByQuery(query)
                    imagesRemoteKeysDao.deleteAll()
                }
                val prevKey = if (loadKey == FIRST_PAGE) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1

                val remoteKeys = images.map { ImageRemoteKeys(it.id, prevKey, nextKey) }

                imagesDao.insertAll(images)
                imagesRemoteKeysDao.insertAll(remoteKeys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun findRemoteKeysForLastItem(state: PagingState<Int, StoredImage>): ImageRemoteKeys? =
        state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { image -> imagesRemoteKeysDao.imageRemoteKeys(image.id) }
}