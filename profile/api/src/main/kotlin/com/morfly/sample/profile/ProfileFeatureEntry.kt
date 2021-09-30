package com.morfly.sample.profile

import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.morfly.sample.common.AggregateFeatureEntry


/**
 * API of the entry point to the 'profile' feature.
 */
abstract class ProfileFeatureEntry : AggregateFeatureEntry {

    /**
     * Declares an entry route of the feature.
     */
    final override val featureRoute = "profile?$ARG_USER_ID={$ARG_USER_ID}"

    /**
     * Declares arguments of an entry route of the feature.
     */
    final override val arguments = listOf(
        navArgument(ARG_USER_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
    )

    /**
     * Builds destination to the profile page of a specific user.
     */
    fun destination(userId: String): String =
        "profile?$ARG_USER_ID=$userId"

    /**
     * Builds destination to the profile page of current user.
     */
    fun myProfileDestination(): String =
        "profile"


    protected companion object {
        const val ARG_USER_ID = "userId"
    }
}