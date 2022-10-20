package io.morfly.streaming.data.impl.mapping

import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.domain.Review
import io.morfly.streaming.data.impl.network.model.MovieDbCredit
import io.morfly.streaming.data.impl.network.model.MovieDbMovie
import io.morfly.streaming.data.impl.network.model.MovieDbReview
import io.morfly.streaming.data.impl.storage.entity.StoredMovie
import io.morfly.streaming.data.impl.storage.entity.StoredReview


interface DataMapper {

    fun networkToStorage(movie: MovieDbMovie, ordinal: Int, query: String): StoredMovie

    fun networkToStorage(review: MovieDbReview, movieId: Int): StoredReview

    fun networkToDomain(movie: MovieDbMovie): Movie

    fun networkToDomain(credit: MovieDbCredit): Credit

    fun storageToDomain(movie: StoredMovie): Movie

    fun storageToDomain(review: StoredReview): Review
}