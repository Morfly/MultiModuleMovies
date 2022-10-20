package io.morfly.streaming.common.ui

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb


@Composable
fun StatusBar(window: Window, color: Color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        DisposableEffect(color) {
            window.statusBarColor = color.toArgb()
            val isLight = color.luminance() > 0.5f
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                if (isLight) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else 0
            onDispose {}
        }
    }
}

@Composable
fun NavigationBar(window: Window, color: Color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DisposableEffect(color) {
            window.navigationBarColor = color.toArgb()
            val isLight = color.luminance() > 0.5f
            if (isLight) {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            onDispose {}
        }
    }
}