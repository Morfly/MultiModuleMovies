package com.morfly.sample.profile.impl.di

import com.morfly.sample.common.FeatureEntry
import com.morfly.sample.common.di.FeatureEntryKey
import com.morfly.sample.profile.ProfileEntry
import com.morfly.sample.profile.impl.ProfileEntryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
interface ProfileEntryModule {

    @Binds
    @Singleton
    @IntoMap
    @FeatureEntryKey(ProfileEntry::class)
    fun profileEntry(entry: ProfileEntryImpl): FeatureEntry
}