package io.morfly.streaming.moviedetails.impl.common.reviews.di

import dagger.Binds
import dagger.Module
import io.morfly.streaming.moviedetails.impl.common.reviews.GetReviews
import io.morfly.streaming.moviedetails.impl.common.reviews.GetReviewsUseCase


@Module
interface ReviewsModule {

    @Binds
    fun bindGetReviews(impl: GetReviewsUseCase): GetReviews
}