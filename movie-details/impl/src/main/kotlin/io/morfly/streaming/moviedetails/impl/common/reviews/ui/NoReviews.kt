package io.morfly.streaming.moviedetails.impl.common.reviews.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.morfly.streaming.moviedetails.impl.R
import kotlinx.coroutines.delay


@Composable
fun NoReviews() {
    var visible by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        val context = LocalContext.current
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(2000)),
        ) {
            Text(context.getString(R.string.no_reviews))
        }
        LaunchedEffect(null) {
            delay(500)
            visible = true
        }
    }
}