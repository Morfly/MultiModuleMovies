package io.morfly.streaming.di

import androidx.compose.runtime.compositionLocalOf
import io.morfly.streaming.common.Destinations
import io.morfly.streaming.common.di.CommonProvider
import io.morfly.streaming.data.DataProvider


interface AppProvider : DataProvider, CommonProvider {

    val destinations: Destinations
}

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }