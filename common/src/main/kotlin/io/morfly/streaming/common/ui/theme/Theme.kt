package io.morfly.streaming.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = AppBlack,
    primaryVariant = AppBlack,
    secondary = AppBlue,
    secondaryVariant = AppBlue,
)

private val LightColorPalette = lightColors(
    primary = AppBlack,
    primaryVariant = AppBlack,
    secondary = AppBlue,
    secondaryVariant = AppBlue,
)

@Composable
fun MovieStreamingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}