package io.morfly.streaming.moviedetails.impl.common.reviews

import androidx.paging.PagingData
import io.morfly.streaming.common.di.CommonModule
import io.morfly.streaming.common.di.CommonModule.IO
import io.morfly.streaming.common.domain.MoviesRepository
import io.morfly.streaming.common.domain.Review
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named


fun interface GetReviews {

    operator fun invoke(movieId: Int): Flow<PagingData<Review>>
}

class GetReviewsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Named(IO) private val ioDispatcher: CoroutineDispatcher
) : GetReviews {

    override fun invoke(movieId: Int): Flow<PagingData<Review>> {
        return moviesRepository.getReviews(movieId).flowOn(ioDispatcher)
    }
}