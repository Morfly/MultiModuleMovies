package io.morfly.streaming.data.impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.morfly.streaming.data.impl.storage.AppDatabase
import javax.inject.Singleton


@Module
object StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        AppDatabase.getInstance(context)
}


