package io.morfly.streaming.moviesearch

import io.morfly.streaming.common.ComposableFeatureEntry


abstract class MovieSearchEntry : ComposableFeatureEntry {

    final override val featureRoute = "movie-search"

    fun destination() = featureRoute
}