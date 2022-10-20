package io.morfly.streaming.data.impl

import androidx.paging.*
import androidx.room.withTransaction
import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.MoviesRepository
import io.morfly.streaming.common.domain.Review
import io.morfly.streaming.data.impl.mapping.DataMapper
import io.morfly.streaming.data.impl.network.MovieDbApi
import io.morfly.streaming.data.impl.storage.AppDatabase
import io.morfly.streaming.data.impl.storage.dao.MoviesDao.Companion.QUERY_TRENDING
import kotlinx.coroutines.flow.*
import java.net.UnknownHostException
import javax.inject.Inject


class DefaultMoviesRepository @Inject constructor(
    private val moviesRemoteMediator: MoviesRemoteMediatorFactory,
    private val reviewsRemoteMediator: ReviewsRemoteMediatorFactory,
    private val networkApi: MovieDbApi,
    private val mapper: DataMapper,
    private val database: AppDatabase,
) : MoviesRepository {

    override fun getMovies(query: String): Flow<PagingData<Movie>> {
        return createPager(query)
    }

    override fun getTrendingMovies(): Flow<List<Movie>> =
        flow {
            val movies = getTrendingMoviesFromNetwork()
            if (movies != null) emit(movies)
            else emit(getTrendingMoviesFromStorage().orEmpty())
        }.onStart {
            val movies = getTrendingMoviesFromStorage()
            if (!movies.isNullOrEmpty()) emit(movies)
        }.distinctUntilChanged()

    internal suspend fun getTrendingMoviesFromNetwork(): List<Movie>? {
        val networkMovies = try {
            networkApi.trendingMovies(MovieDbApi.TIME_WINDOW_WEEK).results
        } catch (e: UnknownHostException) {
            return null
        }
        val domainMovies = networkMovies.map(mapper::networkToDomain)
        val storedMovies = networkMovies.mapIndexed { i, item ->
            mapper.networkToStorage(item, ordinal = i, query = QUERY_TRENDING)
        }
        database.withTransaction {
            database.moviesDao().deleteByQuery(QUERY_TRENDING)
            database.moviesDao().insertAll(storedMovies)
        }
        return domainMovies
    }

    internal suspend fun getTrendingMoviesFromStorage(): List<Movie>? {
        val storedMovies = database.moviesDao().getTrendingMovies()
        if (storedMovies.isEmpty()) return null
        return storedMovies.map(mapper::storageToDomain)
    }

    override suspend fun getMovieById(id: Int): Movie? {
        val storedMovie = database.moviesDao().getMovieById(id) ?: return null
        return mapper.storageToDomain(storedMovie)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun createPager(query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = moviesRemoteMediator.create(query),
            pagingSourceFactory = { database.moviesDao().getMovies(query) }
        ).flow.map { pagingData ->
            pagingData.map(mapper::storageToDomain)
        }

    override suspend fun getCredits(movieId: Int): List<Credit> {
        val networkCredits = networkApi.credits(movieId)
        return networkCredits.cast.map(mapper::networkToDomain)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getReviews(movieId: Int): Flow<PagingData<Review>> =
        Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = reviewsRemoteMediator.create(movieId),
            pagingSourceFactory = { database.reviewsDao().getReviews(movieId) }
        ).flow.map { pagingData ->
            pagingData.map(mapper::storageToDomain)
        }
}
