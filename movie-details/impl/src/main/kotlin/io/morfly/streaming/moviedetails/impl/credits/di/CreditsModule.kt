package io.morfly.streaming.moviedetails.impl.credits.di

import dagger.Binds
import dagger.Module
import io.morfly.streaming.moviedetails.impl.credits.GetCredits
import io.morfly.streaming.moviedetails.impl.credits.GetCreditsUseCase


@Module
interface CreditsModule {

    @Binds
    fun bindGetSortedCredits(impl: GetCreditsUseCase): GetCredits
}