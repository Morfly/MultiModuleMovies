package com.morfly.sample.di

import com.morfly.sample.images.impl.di.ImagesEntryModule
import com.morfly.sample.profile.impl.di.ProfileEntryModule
import dagger.Module


@Module(
    includes = [
        ImagesEntryModule::class,
        ProfileEntryModule::class
    ]
)
interface NavigationModule