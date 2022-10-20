package io.morfly.streaming.common.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getMovies(query: String): Flow<PagingData<Movie>>

    fun getTrendingMovies(): Flow<List<Movie>>

    suspend fun getMovieById(id: Int): Movie?

    suspend fun getCredits(movieId: Int): List<Credit>

    fun getReviews(movieId: Int): Flow<PagingData<Review>>

    companion object {
        const val PAGE_SIZE = 20
    }
}