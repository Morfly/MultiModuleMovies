package io.morfly.streaming.common.di

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController


interface RootComponentHolder<C : Any> {

    val rootRoute: String

    @Composable
    fun rootComponent(
        currentEntry: NavBackStackEntry,
        navController: NavHostController,
        arguments: Bundle?
    ): C {
        val rootEntry = currentEntry.rememberBackStackEntry(navController, route = rootRoute)
        return rootComponent(rootEntry, arguments)
    }

    @Composable
    fun rootComponent(
        rootEntry: NavBackStackEntry,
        arguments: Bundle?
    ): C
}