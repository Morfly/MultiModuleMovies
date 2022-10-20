package io.morfly.streaming.data.impl.network

import io.morfly.streaming.data.impl.network.model.MovieDbCreditsResponse
import io.morfly.streaming.data.impl.network.model.MovieDbMoviesResponse
import io.morfly.streaming.data.impl.network.model.MovieDbReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieDbApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/"
        const val PAGE_SIZE = 20
        const val TIME_WINDOW_DAY = "day"
        const val TIME_WINDOW_WEEK = "week"
    }

    @GET("trending/movie/{time_window}")
    suspend fun trendingMovies(
        @Path("time_window") timeWindow: String
    ): MovieDbMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MovieDbMoviesResponse

    @GET("movie/{movie_id}/credits")
    suspend fun credits(
        @Path("movie_id") movieId: Int
    ): MovieDbCreditsResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun reviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
    ): MovieDbReviewsResponse
}