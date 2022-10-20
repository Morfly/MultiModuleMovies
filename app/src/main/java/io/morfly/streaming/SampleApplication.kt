package io.morfly.streaming

import android.app.Application
import io.morfly.streaming.common.di.DaggerCommonComponent
import io.morfly.streaming.di.DaggerAppComponent
import io.morfly.streaming.data.impl.di.DaggerDataComponent
import io.morfly.streaming.di.AppProvider


class SampleApplication : Application() {

    lateinit var appProvider: AppProvider
        private set

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