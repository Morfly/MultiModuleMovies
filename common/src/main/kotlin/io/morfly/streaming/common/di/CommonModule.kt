package io.morfly.streaming.common.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton


@Module
object CommonModule {

    const val IO = "IO"

    @Provides
    @Singleton
    @Named(IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}