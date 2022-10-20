package io.morfly.streaming.common.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette


fun Drawable.findDominantColor(): Color =
    (this as BitmapDrawable).bitmap.findDominantColor()


fun Bitmap.findDominantColor(): Color {
    val palette = Palette.from(this).generate()

    val rgb = with(palette) {
        getLightVibrantColor(getVibrantColor(getDarkVibrantColor(getDominantColor(0))))
    }
    return Color(rgb)
}