package io.morfly.streaming.moviedetails.impl.credits.di

import dagger.Component
import io.morfly.streaming.common.di.SubfeatureScoped
import io.morfly.streaming.moviedetails.impl.credits.CreditsViewModel
import io.morfly.streaming.moviedetails.impl.di.MovieDetailsComponent


@SubfeatureScoped
@Component(
    dependencies = [MovieDetailsComponent::class],
    modules = [CreditsModule::class]
)
interface CreditsComponent {
    val viewModel: CreditsViewModel
}