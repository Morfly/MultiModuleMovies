package io.morfly.streaming.moviedetails

import androidx.navigation.NavType
import androidx.navigation.navArgument
import io.morfly.streaming.common.AggregateFeatureEntry


/**
 * API of the entry point to the 'movie-details' feature.
 */
abstract class MovieDetailsEntry : AggregateFeatureEntry {

    /**
     * Declares an entry route of the feature.
     */
    final override val featureRoute = "movie-details/{$ARG_MOVIE_ID}"

    /**
     * Declares arguments of an entry route of the feature.
     */
    final override val arguments = listOf(
        navArgument(ARG_MOVIE_ID) {
            type = NavType.IntType
        }
    )

    fun destination(movieId: Int): String =
        "movie-details/$movieId"

    protected companion object {
        const val ARG_MOVIE_ID = "movieId"
    }
}