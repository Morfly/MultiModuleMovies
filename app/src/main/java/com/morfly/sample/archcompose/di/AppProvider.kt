package com.morfly.sample.archcompose.di

import androidx.compose.runtime.compositionLocalOf
import com.morfly.sample.common.Destinations
import com.morfly.sample.common.di.CommonProvider
import com.morfly.sample.data.DataProvider


interface AppProvider : DataProvider, CommonProvider {

    val destinations: Destinations
}

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }