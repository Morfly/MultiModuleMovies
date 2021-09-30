package com.morfly.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.morfly.sample.common.find
import com.morfly.sample.di.LocalAppProvider
import com.morfly.sample.images.ImagesEntry
import com.morfly.sample.profile.ProfileFeatureEntry
import com.morfly.sample.ui.BottomMenuBar


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val destinations = LocalAppProvider.current.destinations

    val imagesScreen = destinations.find<ImagesEntry>()
    val profileScreen = destinations.find<ProfileFeatureEntry>()

    Box(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = imagesScreen.destination()) {
            with(imagesScreen) {
                composable(navController, destinations)
            }
            with(profileScreen) {
                navigation(navController, destinations)
            }
        }
    }

    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
        BottomMenuBar(navController, destinations)
    }
}