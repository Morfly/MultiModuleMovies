package com.morfly.sample.data.impl.di

import com.morfly.sample.common.di.CommonProvider
import com.morfly.sample.data.DataProvider
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [CommonProvider::class],
    modules = [DataModule::class]
)
interface DataComponent : DataProvider