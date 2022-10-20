package io.morfly.streaming.common.di

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import io.morfly.streaming.common.di.CommonModule.IO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named


interface CommonProvider {

    val context: Context

    @get:Named(IO)
    val ioDispatcher: CoroutineDispatcher
}

val LocalCommonProvider = compositionLocalOf<CommonProvider> { error("No common provider found!") }