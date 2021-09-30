package com.morfly.sample.common.di

import android.content.Context
import androidx.compose.runtime.compositionLocalOf


interface CommonProvider {

    val context: Context
}

val LocalCommonProvider = compositionLocalOf<CommonProvider> { error("No common provider found!") }