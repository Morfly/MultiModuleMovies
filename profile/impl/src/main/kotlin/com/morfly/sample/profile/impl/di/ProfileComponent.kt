package com.morfly.sample.profile.impl.di

import com.morfly.sample.common.di.FeatureScoped
import com.morfly.sample.data.DataProvider
import com.morfly.sample.profile.ProfileProvider
import dagger.Component


@FeatureScoped
@Component(
    dependencies = [DataProvider::class],
    modules = [ProfileModule::class]
)
interface ProfileComponent : ProfileProvider, DataProvider