package io.morfly.streaming.moviesearch.impl.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.morfly.streaming.common.FeatureEntry
import io.morfly.streaming.common.di.FeatureEntryKey
import io.morfly.streaming.moviesearch.MovieSearchEntry
import io.morfly.streaming.moviesearch.impl.MovieSearchEntryImpl
import javax.inject.Singleton


@Module
interface MovieSearchEntryModule {

    @Binds
    @Singleton
    @IntoMap
    @FeatureEntryKey(MovieSearchEntry::class)
    fun movieSearchEntry(entry: MovieSearchEntryImpl): FeatureEntry
}