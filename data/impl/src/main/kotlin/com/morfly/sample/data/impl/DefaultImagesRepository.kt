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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.ImagesRepository
import com.morfly.sample.data.impl.mapping.DataMapper
import com.morfly.sample.data.impl.network.PixabayApi
import com.morfly.sample.data.impl.storage.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DefaultImagesRepository @Inject constructor(
    private val networkService: PixabayApi,
    private val database: AppDatabase,
    private val mapper: DataMapper,
) : ImagesRepository {

    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedImages(query: String): Flow<PagingData<Image>> =
        Pager(
            config = PagingConfig(
                pageSize = ImagesRepository.PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = ImagesRemoteMediator(query, networkService, database, mapper),
            pagingSourceFactory = { database.imagesDao().getImages(query) }
        ).flow.flowOn(Dispatchers.Default) as Flow<PagingData<Image>>

    override fun getUserImages(userId: String): Flow<List<Image>> =
        database.imagesDao().getUserImages(userId)

    override suspend fun getImage(id: String): Image? =
        database.imagesDao().getImageById(id)
}