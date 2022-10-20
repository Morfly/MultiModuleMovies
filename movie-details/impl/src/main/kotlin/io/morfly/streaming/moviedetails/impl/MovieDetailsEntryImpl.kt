package io.morfly.streaming.moviedetails.impl

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.morfly.streaming.common.Destinations
import io.morfly.streaming.common.di.*
import io.morfly.streaming.data.LocalDataProvider
import io.morfly.streaming.moviedetails.MovieDetailsEntry
import io.morfly.streaming.moviedetails.impl.credits.di.DaggerCreditsComponent
import io.morfly.streaming.moviedetails.impl.credits.ui.CreditsScreen
import io.morfly.streaming.moviedetails.impl.di.DaggerMovieDetailsComponent
import io.morfly.streaming.moviedetails.impl.di.MovieDetailsComponent
import io.morfly.streaming.moviedetails.impl.movie.di.DaggerMovieComponent
import io.morfly.streaming.moviedetails.impl.movie.ui.MovieScreen
import javax.inject.Inject


class MovieDetailsEntryImpl @Inject constructor() : MovieDetailsEntry(),
    RootComponentHolder<MovieDetailsComponent> {

    override val rootRoute = "@movie-details"

    private val creditsRoute = "credits/{$ARG_MOVIE_ID}"
    private fun creditsDestination(movieId: Int) = "credits/$movieId"

    override fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: Destinations
    ) {
        navigation(startDestination = featureRoute, route = rootRoute) {

            composable(route = featureRoute, arguments) {
                val movieId = it.arguments?.getInt(ARG_MOVIE_ID)!!

                val rootEntry = it.rememberBackStackEntry(navController, route = rootRoute)
                val rootComponent = rootComponent(rootEntry, it.arguments)

                val viewModel = injectedViewModel(rootEntry) {
                    DaggerMovieComponent.builder()
                        .movieDetailsComponent(rootComponent)
                        .build()
                        .viewModel
                }

                MovieScreen(
                    viewModel,
                    onShowCredits = { navController.navigate(creditsDestination(movieId)) },
                )
            }

            composable(route = creditsRoute, arguments) {
                val rootComponent = rootComponent(currentEntry = it, navController, it.arguments)

                val viewModel = injectedViewModel {
                    DaggerCreditsComponent.builder()
                        .movieDetailsComponent(rootComponent)
                        .build()
                        .viewModel
                }

                CreditsScreen(viewModel)
            }
        }
    }

    @Composable
    override fun rootComponent(
        rootEntry: NavBackStackEntry,
        arguments: Bundle?
    ): MovieDetailsComponent {
        val movieId = arguments?.getInt(ARG_MOVIE_ID)!!
        val dataProvider = LocalDataProvider.current
        val commonProvider = LocalCommonProvider.current

        return rememberScoped(rootEntry) {
            DaggerMovieDetailsComponent.factory()
                .create(dataProvider, commonProvider, movieId)
        }
    }
}