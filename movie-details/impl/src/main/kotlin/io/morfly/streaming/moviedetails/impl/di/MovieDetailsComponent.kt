package io.morfly.streaming.moviedetails.impl.di

import dagger.BindsInstance
import dagger.Component
import io.morfly.streaming.common.di.CommonProvider
import io.morfly.streaming.common.di.FeatureScoped
import io.morfly.streaming.data.DataProvider
import io.morfly.streaming.moviedetails.MovieDetailsProvider
import io.morfly.streaming.moviedetails.impl.movie.GetMovie


@FeatureScoped
@Component(
    dependencies = [DataProvider::class, CommonProvider::class],
    modules = [MovieDetailsModule::class]
)
interface MovieDetailsComponent : MovieDetailsProvider, DataProvider, CommonProvider {
    val getMovie: GetMovie

    @get:MovieId
    val movieId: Int

    @Component.Factory
    interface Factory {

        fun create(
            dataProvider: DataProvider,
            commonProvider: CommonProvider,
            @BindsInstance @MovieId movieId: Int
        ): MovieDetailsComponent
    }
}