package io.morfly.streaming.moviedetails.impl.di

import dagger.Binds
import dagger.Module
import io.morfly.streaming.moviedetails.impl.movie.GetMovie
import io.morfly.streaming.moviedetails.impl.movie.GetMovieUseCase


@Module
interface MovieDetailsModule {

    @Binds
    fun bindGetMovies(impl: GetMovieUseCase): GetMovie
}