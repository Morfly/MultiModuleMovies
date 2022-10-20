package io.morfly.streaming.moviedetails.impl.movie.di

import dagger.Module
import io.morfly.streaming.moviedetails.impl.common.reviews.di.ReviewsModule


@Module(includes = [ReviewsModule::class])
interface MovieModule