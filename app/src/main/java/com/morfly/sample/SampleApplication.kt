package com.morfly.sample

import android.app.Application
import com.morfly.sample.common.di.DaggerCommonComponent
import com.morfly.sample.data.impl.di.DaggerDataComponent
import com.morfly.sample.di.AppProvider
import com.morfly.sample.di.DaggerAppComponent
import com.morfly.sample.images.impl.di.DaggerImagesComponent
import com.morfly.sample.profile.impl.di.DaggerProfileComponent


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