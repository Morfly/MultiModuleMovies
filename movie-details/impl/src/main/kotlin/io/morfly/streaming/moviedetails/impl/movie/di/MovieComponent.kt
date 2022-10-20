package io.morfly.streaming.moviedetails.impl.movie.di

import dagger.Component
import io.morfly.streaming.common.di.CommonProvider
import io.morfly.streaming.common.di.SubfeatureScoped
import io.morfly.streaming.moviedetails.impl.di.MovieDetailsComponent
import io.morfly.streaming.moviedetails.impl.movie.MovieViewModel


@SubfeatureScoped
@Component(
    dependencies = [MovieDetailsComponent::class],
    modules = [MovieModule::class]
)
interface MovieComponent {
    val viewModel: MovieViewModel
}