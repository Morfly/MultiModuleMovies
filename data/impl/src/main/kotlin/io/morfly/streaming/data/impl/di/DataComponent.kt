package io.morfly.streaming.data.impl.di

import dagger.Component
import io.morfly.streaming.common.di.CommonProvider
import io.morfly.streaming.data.DataProvider
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [CommonProvider::class],
    modules = [DataModule::class]
)
interface DataComponent : DataProvider