package com.morfly.sample.profile.impl.di

import com.morfly.sample.common.FeatureEntry
import com.morfly.sample.common.di.FeatureEntryKey
import com.morfly.sample.profile.ProfileFeatureEntry
import com.morfly.sample.profile.impl.ProfileFeatureEntryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
interface ProfileEntryModule {

    @Binds
    @Singleton
    @IntoMap
    @FeatureEntryKey(ProfileFeatureEntry::class)
    fun profileEntry(entry: ProfileFeatureEntryImpl): FeatureEntry
}