package io.morfly.streaming.common.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun ProgressiveGlowingImage(
    url: String,
    thumbUrl: String,
    glow: Boolean,
    modifier: Modifier = Modifier
) {
    var glowColor by remember { mutableStateOf(Color.White) }
    val animatedGlowColor by animateColorAsState(
        targetValue = glowColor,
        animationSpec = tween(1500)
    )
    if (glow) {
        Box(
            modifier
                .padding(10.dp)
                .glow(
                    color = animatedGlowColor,
                    radius = 25.dp,
                    alpha = 0.5f,
                    offsetY = 12.dp
                )
        ) {
            ProgressiveGlowingImage(url, thumbUrl) { glowColor = it }
        }
    } else {
        ProgressiveGlowingImage(url, thumbUrl, null)
    }
}

@Composable
fun ProgressiveGlowingImage(
    url: String,
    thumbUrl: String,
    onDominantColorLoaded: ((Color) -> Unit)?
) {
    val shouldGlow = onDominantColorLoaded != null
    var showShimmer by remember { mutableStateOf(true) }
    var showThumb by remember { mutableStateOf(true) }

    var imageModifier: Modifier = Modifier
    if (shouldGlow) {
        imageModifier = imageModifier.clip(RoundedCornerShape(15.dp))
    }
    imageModifier = imageModifier
        .fillMaxWidth()
        .requiredHeight(240.dp)

    Box {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .allowHardware(false)
                    .build(),
                error = ColorPainter(Color.LightGray),
                onSuccess = { state ->
                    showThumb = false
                    showShimmer = false
                    if (shouldGlow) {
                        val bitmap = (state.painter as? BitmapPainter)?.bitmap()
                        val color = bitmap?.findDominantColor()
                        color?.let(onDominantColorLoaded!!)
                    }
                },
                onError = {
                    showShimmer = false
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = imageModifier
        )

        if (showThumb) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(thumbUrl)
                        .crossfade(true)
                        .allowHardware(false)
                        .build(),
                    onLoading = { showShimmer = true },
                    onSuccess = { state ->
                        showShimmer = false
                        if (shouldGlow) {
                            val bitmap = (state.painter as? BitmapPainter)?.bitmap()
                            val color = bitmap?.findDominantColor()
                            color?.let(onDominantColorLoaded!!)
                        }
                    },
                    onError = { showShimmer = false }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = imageModifier
                    .background(if (showShimmer) Color.White else Color.Transparent),
            )
        }

        if (showShimmer) {
            LoadingShimmerEffect { brush ->
                Spacer(modifier = imageModifier.background(brush))
            }
        }
    }
}