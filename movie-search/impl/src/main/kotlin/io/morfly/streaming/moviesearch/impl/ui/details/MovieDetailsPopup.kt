package io.morfly.streaming.moviesearch.impl.ui.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.morfly.streaming.common.domain.Movie
import io.morfly.streaming.common.ui.Dimens.bottomPadding
import io.morfly.streaming.common.ui.theme.AppBlackTransparent
import io.morfly.streaming.moviesearch.impl.ui.details.State.*


private enum class State {
    PreDisplayed, Displayed, PostDisplayed
}

@Composable
fun MovieDetailsPopup(
    movie: Movie,
    onClosed: () -> Unit,
    onExpandMovieDetails: (Movie) -> Unit
) {
    var state by remember { mutableStateOf(PreDisplayed) }
    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            PreDisplayed, PostDisplayed -> Color.Transparent
            Displayed -> AppBlackTransparent
        },
        animationSpec = tween(300),
        finishedListener = {
            if (state == PostDisplayed) onClosed()
        }
    )
    val offset by animateDpAsState(
        targetValue = when (state) {
            PreDisplayed, PostDisplayed -> 1000.dp
            Displayed -> 0.dp
        },
        animationSpec = tween(300)
    )

    LaunchedEffect(Unit) {
        state = Displayed
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        Modifier.clickable(interactionSource, null) { state = PostDisplayed },
        contentAlignment = Alignment.BottomCenter
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        )
        MovieDetailsCard(
            movie,
            Modifier
                .offset(y = offset)
                .padding(bottom = bottomPadding)
                .clickable(interactionSource, null) {},
            onClose = { state = PostDisplayed },
            onExpandMovieDetails
        )
    }
}