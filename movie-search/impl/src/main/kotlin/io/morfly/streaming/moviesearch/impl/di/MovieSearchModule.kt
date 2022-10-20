package io.morfly.streaming.moviesearch.impl.di

import dagger.Binds
import dagger.Module
import io.morfly.streaming.moviesearch.impl.GetMovies
import io.morfly.streaming.moviesearch.impl.GetMoviesUseCase


@Module
interface MovieSearchModule {

    @Binds
    fun bindSearchMovies(impl: GetMoviesUseCase): GetMovies
}