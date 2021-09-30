package com.morfly.sample.images

import com.morfly.sample.common.ComposableFeatureEntry


abstract class ImagesEntry : ComposableFeatureEntry {

    final override val featureRoute = "images"

    fun destination() = featureRoute
}