package io.morfly.streaming

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.morfly.streaming.common.find
import io.morfly.streaming.di.LocalAppProvider
import io.morfly.streaming.moviedetails.MovieDetailsEntry
import io.morfly.streaming.moviesearch.MovieSearchEntry
import io.morfly.streaming.ui.BottomMenuBar


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val destinations = LocalAppProvider.current.destinations

    val movieSearchScreen = destinations.find<MovieSearchEntry>()
    val movieDetailsScreen = destinations.find<MovieDetailsEntry>()

    Box(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = movieSearchScreen.destination()) {

            with(movieSearchScreen) {
                composable(navController, destinations)
            }
            with(movieDetailsScreen) {
                navigation(navController, destinations)
            }
        }
    }

    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
        BottomMenuBar(navController, destinations)
    }
}