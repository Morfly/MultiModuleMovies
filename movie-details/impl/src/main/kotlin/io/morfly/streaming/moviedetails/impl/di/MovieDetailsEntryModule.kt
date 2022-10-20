package io.morfly.streaming.moviedetails.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.morfly.streaming.common.FeatureEntry
import io.morfly.streaming.common.di.FeatureEntryKey
import io.morfly.streaming.moviedetails.MovieDetailsEntry
import io.morfly.streaming.moviedetails.impl.MovieDetailsEntryImpl
import javax.inject.Singleton


@Module
interface MovieDetailsEntryModule {

    @Binds
    @Singleton
    @IntoMap
    @FeatureEntryKey(MovieDetailsEntry::class)
    fun movieSearchEntry(entry: MovieDetailsEntryImpl): FeatureEntry
}