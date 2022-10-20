package io.morfly.streaming.common.ui

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter


fun BitmapPainter.bitmap(): Bitmap {
    val field = javaClass.getDeclaredField("image")
    field.isAccessible = true
    return (field.get(this) as ImageBitmap).asAndroidBitmap()
}