package io.morfly.streaming.di

import dagger.Module
import io.morfly.streaming.moviedetails.impl.di.MovieDetailsEntryModule
import io.morfly.streaming.moviesearch.impl.di.MovieSearchEntryModule


@Module(
    includes = [
        MovieSearchEntryModule::class,
        MovieDetailsEntryModule::class,
    ]
)
interface NavigationModule