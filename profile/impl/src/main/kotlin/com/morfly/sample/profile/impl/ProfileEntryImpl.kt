@file:SuppressLint("ComposableNaming")

package com.morfly.sample.profile.impl

import android.annotation.SuppressLint
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.morfly.sample.common.Destinations
import com.morfly.sample.common.di.injectedViewModel
import com.morfly.sample.data.DataProvider
import com.morfly.sample.data.LocalDataProvider
import com.morfly.sample.profile.ProfileEntry
import com.morfly.sample.profile.impl.di.DaggerProfileComponent
import com.morfly.sample.profile.impl.settings.di.DaggerSettingsComponent
import com.morfly.sample.profile.impl.settings.ui.SettingsScreen
import com.morfly.sample.profile.impl.userprofile.di.DaggerUserProfileComponent
import com.morfly.sample.profile.impl.userprofile.ui.UserProfileScreen
import javax.inject.Inject


class ProfileEntryImpl @Inject constructor() : ProfileEntry() {

    override fun NavGraphBuilder.navigation(
        navController: NavHostController,
        destinations: Destinations
    ) {
        navigation(startDestination = myProfileDestination(), route = "@profile") {

            composable(route = featureRoute, arguments) {
                val userId = it.arguments?.getString(ARG_USER_ID)

                val viewModel = injectedViewModel {
                    DaggerUserProfileComponent.factory()
                        .create(buildRootProfileComponent(LocalDataProvider.current), userId)
                        .viewModel
                }
                UserProfileScreen(navController, viewModel)
            }

            composable(route = InternalRoutes.SETTINGS) {
                val viewModel = injectedViewModel {
                    DaggerSettingsComponent.builder()
                        .profileComponent(buildRootProfileComponent(LocalDataProvider.current))
                        .build()
                        .viewModel
                }
                SettingsScreen(viewModel)
            }
        }
    }

    private fun buildRootProfileComponent(dataProvider: DataProvider) =
        DaggerProfileComponent.builder().dataProvider(dataProvider).build()


    internal object InternalRoutes {
        const val SETTINGS = "settings"
    }
}