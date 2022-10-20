package io.morfly.streaming.di

import dagger.Component
import io.morfly.streaming.common.di.CommonProvider
import io.morfly.streaming.data.DataProvider
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