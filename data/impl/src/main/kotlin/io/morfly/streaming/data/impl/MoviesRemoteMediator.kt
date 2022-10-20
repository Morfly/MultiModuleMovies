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
import io.morfly.streaming.data.impl.storage.entity.MovieRemoteKeys
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import retrofit2.HttpException
import java.io.IOException


private const val FIRST_PAGE = 1
private const val SECOND_PAGE = 2

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @AssistedInject constructor(
    @Assisted private val query: String,
    private val networkService: MovieDbApi,
    private val database: AppDatabase,
    private val mapper: DataMapper
) : RemoteMediator<Int, StoredMovie>() {

    private val moviesDao = database.moviesDao()
    private val movieRemoteKeysDao = database.movieRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, StoredMovie>
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

            val offset = calculateOffset(MovieDbApi.PAGE_SIZE, loadKey)

            val movies = networkService.searchMovies(query, page = loadKey).results
                .filter { it.posterPath != null }
                .mapIndexed { i, item ->
                    mapper.networkToStorage(item, ordinal = offset + i, query)
                }

            val endOfPaginationReached = movies.isEmpty()

            val prevKey = if (loadKey == FIRST_PAGE) null else loadKey - 1
            val nextKey = if (endOfPaginationReached) null else loadKey + 1
            val remoteKeys = movies.map { MovieRemoteKeys(it.localId, prevKey, nextKey) }

            writeToDatabase(movies, remoteKeys)

            MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    fun calculateOffset(pageSize: Int, loadKey: Int): Int {
        return pageSize * loadKey - pageSize
    }

    internal suspend fun writeToDatabase(
        movies: List<StoredMovie>,
        remoteKeys: List<MovieRemoteKeys>
    ) {
        database.withTransaction {
            moviesDao.insertAll(movies)
            movieRemoteKeysDao.insertAll(remoteKeys)
        }
    }

    internal fun findLastItem(state: PagingState<Int, StoredMovie>): StoredMovie? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
    }

    private suspend fun findRemoteKeysForLastItem(state: PagingState<Int, StoredMovie>): MovieRemoteKeys? =
        findLastItem(state)
            ?.let { movie -> movieRemoteKeysDao.movieRemoteKeys(movie.localId) }
}