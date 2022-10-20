package io.morfly.streaming.common.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun GlowingMenuIcon(
    isGlowing: Boolean,
    glowingIcon: ImageVector,
    idleIcon: ImageVector,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.White,
    glowColor: Color = iconColor,
) {
    val icon = if (isGlowing) glowingIcon else idleIcon
    val alpha by animateFloatAsState(if (isGlowing) 0.45f else 0f)

    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier
            .padding(15.dp)
            .glow(
                color = glowColor,
                alpha = alpha,
                radius = 20.dp
            ),
        tint = iconColor
    )
}