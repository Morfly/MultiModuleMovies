package com.morfly.sample.archcompose.di

import com.morfly.sample.common.di.CommonProvider
import com.morfly.sample.data.DataProvider
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