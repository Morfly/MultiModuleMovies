package com.morfly.sample.common.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface CommonComponent : CommonProvider {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): CommonComponent
    }
}