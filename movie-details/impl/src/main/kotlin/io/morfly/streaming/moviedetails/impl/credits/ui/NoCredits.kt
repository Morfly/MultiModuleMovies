package io.morfly.streaming.moviedetails.impl.credits.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.morfly.streaming.moviedetails.impl.R
import kotlinx.coroutines.delay


@Composable
fun NoCredits() {
    var visible by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(2000)),
        ) {
            Text(context.getString(R.string.no_credits))
        }
        LaunchedEffect(null) {
            delay(500)
            visible = true
        }
    }
}