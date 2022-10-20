package io.morfly.streaming.moviedetails.impl.common.reviews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.morfly.streaming.common.domain.Review
import io.morfly.streaming.moviedetails.impl.R
import io.morfly.streaming.moviedetails.impl.common.reviews.ui.ReviewItemType.EVEN
import io.morfly.streaming.moviedetails.impl.common.reviews.ui.ReviewItemType.ODD


@Composable
fun Author(review: Review, type: ReviewItemType) {
    val context = LocalContext.current

    val avatarPlaceholderColor = if (review.author.avatarPath != null) {
        Color.Transparent
    } else when (type) {
        ODD -> Color.White
        EVEN -> Color(0xFFF5F5F5)
    }

    val horizontalAlignment = when(type) {
        ODD -> Alignment.Start
        EVEN -> Alignment.End
    }

    Row{
        if (type == ODD) {
            AsyncImage(
                model = review.author.avatarPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(43.dp)
                    .clip(RoundedCornerShape(50))
                    .background(avatarPlaceholderColor)
            )
            Spacer(Modifier.width(10.dp))
        }
        Column(Modifier.height(43.dp), horizontalAlignment = horizontalAlignment) {
            Text(
                text = review.author.name.ifBlank { context.getString(R.string.anonymous) },
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = review.author.username,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1
            )
        }
        if (type == EVEN) {
            Spacer(Modifier.width(10.dp))
            AsyncImage(
                model = review.author.avatarPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(43.dp)
                    .clip(RoundedCornerShape(50))
                    .background(avatarPlaceholderColor)
            )
        }
    }
}