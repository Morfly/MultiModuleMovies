package io.morfly.streaming.moviesearch.impl

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import io.morfly.streaming.common.di.CommonModule.IO
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Named


fun interface GetMovies {

    operator fun invoke(query: String?): Flow<PagingData<Movie>>
}

class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
) : GetMovies {

    override fun invoke(query: String?): Flow<PagingData<Movie>> {
        val movies = if (query.isNullOrBlank()) getTrendingMovies() else searchMovies(query)
        return movies.flowOn(ioDispatcher)
    }

    internal fun getTrendingMovies(): Flow<PagingData<Movie>> =
        moviesRepository.getTrendingMovies().map { movies ->
            val state = LoadState.NotLoading(endOfPaginationReached = true)
            val states = LoadStates(state, state, state)
            PagingData.from(movies, states)
        }.onStart {
            val state = LoadState.Loading
            val states = LoadStates(state, state, state)
            emit(PagingData.empty(states))
        }

    internal fun searchMovies(query: String): Flow<PagingData<Movie>> =
        moviesRepository.getMovies(query)
}