package com.morfly.sample.images.impl.di

import com.morfly.sample.common.di.FeatureScoped
import com.morfly.sample.data.DataProvider
import com.morfly.sample.images.ImagesProvider
import com.morfly.sample.images.impl.ImagesViewModel
import dagger.Component


@FeatureScoped
@Component(
    dependencies = [DataProvider::class],
    modules = [ImagesModule::class]
)
interface ImagesComponent : ImagesProvider {

    val viewModel: ImagesViewModel
}