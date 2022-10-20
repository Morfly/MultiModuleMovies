package io.morfly.streaming.moviesearch.impl.di

import dagger.Component
import io.morfly.streaming.common.di.CommonProvider
import io.morfly.streaming.common.di.FeatureScoped
import io.morfly.streaming.data.DataProvider
import io.morfly.streaming.moviesearch.MovieSearchProvider
import io.morfly.streaming.moviesearch.impl.MovieSearchViewModel


@FeatureScoped
@Component(
    dependencies = [DataProvider::class, CommonProvider::class],
    modules = [MovieSearchModule::class]
)
interface MovieSearchComponent : MovieSearchProvider {

    val viewModel: MovieSearchViewModel
}