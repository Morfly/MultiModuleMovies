package io.morfly.streaming.moviedetails.impl.credits

import io.morfly.streaming.common.domain.Credit
import io.morfly.streaming.common.domain.MoviesRepository
import javax.inject.Inject


typealias Department = String

fun interface GetCredits {

    suspend operator fun invoke(movieId: Int): Map<Department, List<Credit>>
}

class GetCreditsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetCredits {

    override suspend fun invoke(movieId: Int): Map<Department, List<Credit>> {
        return moviesRepository.getCredits(movieId)
            .sortedBy { it.order }
            .groupBy { it.department }
            .toSortedMap()
    }
}