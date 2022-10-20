package io.morfly.streaming.data.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.morfly.streaming.data.impl.mapping.DataMapper
import io.morfly.streaming.data.impl.network.MovieDbApi
import io.morfly.streaming.data.impl.storage.AppDatabase
import io.morfly.streaming.data.impl.storage.entity.ReviewRemoteKeys
import io.morfly.streaming.data.impl.storage.entity.StoredReview
import retrofit2.HttpException
import java.io.IOException


private const val FIRST_PAGE = 1
private const val SECOND_PAGE = 2

@OptIn(ExperimentalPagingApi::class)
class ReviewsRemoteMediator @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val networkService: MovieDbApi,
    private val database: AppDatabase,
    private val mapper: DataMapper
) : RemoteMediator<Int, StoredReview>() {

    private val reviewsDao = database.reviewsDao()
    private val reviewRemoteKeysDao = database.reviewRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoredReview>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItemRemoteKeys = findRemoteKeysForLastItem(state)

                    if (lastItemRemoteKeys?.nextKey == null) {
                        if (state.pages.isNotEmpty() && state.pages.lastOrNull { it.data.isNotEmpty() } == null) SECOND_PAGE
                        else return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        lastItemRemoteKeys.nextKey
                    }
                }
            }

            val movies = networkService.reviews(movieId, page = loadKey).results
                .map { item -> mapper.networkToStorage(item, movieId) }

            val endOfPaginationReached = movies.isEmpty()

            val prevKey = if (loadKey == FIRST_PAGE) null else loadKey - 1
            val nextKey = if (endOfPaginationReached) null else loadKey + 1
            val remoteKeys = movies.map { ReviewRemoteKeys(it.id, prevKey, nextKey) }

            writeToDatabase(movies, remoteKeys)

            MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun writeToDatabase(
        movies: List<StoredReview>,
        remoteKeys: List<ReviewRemoteKeys>
    ) {
        database.withTransaction {
            reviewsDao.insertAll(movies)
            reviewRemoteKeysDao.insertAll(remoteKeys)
        }
    }

    private fun findLastItem(state: PagingState<Int, StoredReview>): StoredReview? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
    }

    private suspend fun findRemoteKeysForLastItem(state: PagingState<Int, StoredReview>): ReviewRemoteKeys? =
        findLastItem(state)
            ?.let { review -> reviewRemoteKeysDao.reviewRemoteKeys(review.id) }
}