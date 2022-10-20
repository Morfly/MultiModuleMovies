package io.morfly.streaming.moviedetails.impl.common.reviews.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import io.morfly.streaming.common.ui.Dimens
import io.morfly.streaming.moviedetails.impl.common.reviews.ReviewItem
import io.morfly.streaming.moviedetails.impl.common.reviews.ui.ReviewItemType.EVEN
import io.morfly.streaming.moviedetails.impl.common.reviews.ui.ReviewItemType.ODD


enum class ReviewItemType {
    EVEN, ODD
}

const val COLLAPSED_CONTENT_LENGTH_LIMIT = 100

@Composable
fun ReviewList(reviews: LazyPagingItems<ReviewItem>, header: @Composable () -> Unit) {
    LazyColumn(contentPadding = PaddingValues(bottom = Dimens.bottomPadding + 15.dp)) {
        item {
            header()
        }
        items(reviews.itemCount) { index ->
            var visible by remember { mutableStateOf(false) }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(300)),
            ) {
                reviews[index]?.let { review ->
                    val itemType = if (index % 2 == 0) ODD else EVEN
                    ReviewItem(review, itemType)
                }
            }
            LaunchedEffect(null) {
                visible = true
            }
        }
    }
}
