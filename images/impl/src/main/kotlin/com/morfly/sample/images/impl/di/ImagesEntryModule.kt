package com.morfly.sample.images.impl.di

import com.morfly.sample.common.FeatureEntry
import com.morfly.sample.common.di.FeatureEntryKey
import com.morfly.sample.images.ImagesEntry
import com.morfly.sample.images.impl.ImagesEntryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


// Used by AppComponent directly
@Module
interface ImagesEntryModule {

    @Binds
    @Singleton
    @IntoMap
    @FeatureEntryKey(ImagesEntry::class)
    fun imagesEntry(entry: ImagesEntryImpl): FeatureEntry
}