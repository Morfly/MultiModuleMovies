package com.morfly.sample.di

import androidx.compose.runtime.compositionLocalOf
import com.morfly.sample.common.Destinations
import com.morfly.sample.common.di.CommonProvider
import com.morfly.sample.data.DataProvider
import com.morfly.sample.data.impl.di.DataComponent


interface AppProvider : DataProvider, CommonProvider {

    val destinations: Destinations
}

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }