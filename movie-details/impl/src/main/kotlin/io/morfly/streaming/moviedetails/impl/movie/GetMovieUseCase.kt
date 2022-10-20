package io.morfly.streaming.moviedetails.impl.movie

import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.MoviesRepository
import javax.inject.Inject


fun interface GetMovie {

    suspend operator fun invoke(id: Int): Movie?
}

class GetMovieUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovie {

    override suspend fun invoke(id: Int): Movie? {
        return moviesRepository.getMovieById(id)
    }
}