package io.morfly.streaming.moviedetails.impl.common.reviews

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import io.morfly.streaming.common.domain.Review
import io.morfly.streaming.moviedetails.impl.common.reviews.ReviewItemState.COLLAPSED
import kotlinx.coroutines.flow.Flow


enum class ReviewItemState {
    COLLAPSED, EXPANDED
}

data class ReviewItem(
    val review: Review,
    val state: MutableState<ReviewItemState> = mutableStateOf(COLLAPSED)
)

interface ReviewsViewModel {

    val reviews: Flow<PagingData<ReviewItem>>
}