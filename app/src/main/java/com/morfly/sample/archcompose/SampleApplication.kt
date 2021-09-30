package com.morfly.sample.archcompose

import android.app.Application
import com.morfly.sample.common.di.DaggerCommonComponent
import com.morfly.sample.data.impl.di.DaggerDataComponent
import com.morfly.sample.archcompose.di.AppProvider
import com.morfly.sample.archcompose.di.DaggerAppComponent


class SampleApplication : Application() {

    lateinit var appProvider: AppProvider

    override fun onCreate() {
        super.onCreate()

        val commonProvider = DaggerCommonComponent.factory().create(this)
        appProvider = DaggerAppComponent.builder()
            .commonProvider(commonProvider)
            .dataProvider(DaggerDataComponent.builder().commonProvider(commonProvider).build())
            .build()
    }
}

val Application.appProvider: AppProvider
    get() = (this as SampleApplication).appProvider