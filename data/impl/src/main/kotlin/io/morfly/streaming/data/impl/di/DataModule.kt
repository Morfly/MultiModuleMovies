package io.morfly.streaming.data.impl.di

import dagger.Binds
import dagger.Module
import io.morfly.streaming.common.domain.MoviesRepository
import io.morfly.streaming.data.impl.DefaultMoviesRepository
import io.morfly.streaming.data.impl.mapping.DataMapper
import io.morfly.streaming.data.impl.mapping.DefaultDataMapper
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, StorageModule::class])
interface DataModule {

    @Binds
    @Singleton
    fun mapper(impl: DefaultDataMapper): DataMapper

    @Binds
    @Singleton
    fun moviesRepository(impl: DefaultMoviesRepository): MoviesRepository
}