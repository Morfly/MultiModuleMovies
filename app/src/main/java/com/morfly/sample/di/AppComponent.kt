package com.morfly.sample.di

import com.morfly.sample.common.di.CommonProvider
import com.morfly.sample.data.DataProvider
import com.morfly.sample.images.ImagesProvider
import com.morfly.sample.profile.ProfileProvider
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [
        CommonProvider::class,
        DataProvider::class,
    ],
    modules = [NavigationModule::class]
)
interface AppComponent : AppProvider