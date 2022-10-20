package io.morfly.streaming.data

import androidx.compose.runtime.compositionLocalOf
import io.morfly.streaming.common.domain.MoviesRepository


interface DataProvider {

    val moviesRepository: MoviesRepository
}

val LocalDataProvider = compositionLocalOf<DataProvider> { error("No data provider found!") }