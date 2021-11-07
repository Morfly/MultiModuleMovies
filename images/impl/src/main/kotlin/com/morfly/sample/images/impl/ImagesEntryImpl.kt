package com.morfly.sample.images.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.morfly.sample.common.Destinations
import com.morfly.sample.common.di.injectedViewModel
import com.morfly.sample.common.find
import com.morfly.sample.data.LocalDataProvider
import com.morfly.sample.images.ImagesEntry
import com.morfly.sample.images.impl.di.DaggerImagesComponent
import com.morfly.sample.images.impl.ui.ImageListScreen
import com.morfly.sample.profile.ProfileEntry
import javax.inject.Inject


class ImagesEntryImpl @Inject constructor() : ImagesEntry() {

    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        val viewModel = injectedViewModel {
            DaggerImagesComponent.builder()
                .dataProvider(LocalDataProvider.current)
                .build()
                .viewModel
        }

        ImageListScreen(viewModel, onUserSelected = { userId ->
            val profileDestination = destinations
                .find<ProfileEntry>()
                .destination(userId)
            navController.navigate(profileDestination)
        })
    }
}