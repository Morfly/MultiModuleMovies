package io.morfly.streaming.moviesearch.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import io.morfly.streaming.common.Destinations
import io.morfly.streaming.common.di.LocalCommonProvider
import io.morfly.streaming.common.di.injectedViewModel
import io.morfly.streaming.common.find
import io.morfly.streaming.data.LocalDataProvider
import io.morfly.streaming.moviedetails.MovieDetailsEntry
import io.morfly.streaming.moviesearch.MovieSearchEntry
import io.morfly.streaming.moviesearch.impl.di.DaggerMovieSearchComponent
import io.morfly.streaming.moviesearch.impl.ui.MovieSearchScreen
import javax.inject.Inject


class MovieSearchEntryImpl @Inject constructor() : MovieSearchEntry() {

    @Composable
    override fun Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        val dataProvider = LocalDataProvider.current
        val commonProvider = LocalCommonProvider.current
        val viewModel = injectedViewModel {
            DaggerMovieSearchComponent.builder()
                .dataProvider(dataProvider)
                .commonProvider(commonProvider)
                .build()
                .viewModel
        }

        MovieSearchScreen(viewModel, onExpandMovieDetails = { movie ->
            val destination = destinations
                .find<MovieDetailsEntry>()
                .destination(movie.id)
            navController.navigate(destination)
        })
    }
}