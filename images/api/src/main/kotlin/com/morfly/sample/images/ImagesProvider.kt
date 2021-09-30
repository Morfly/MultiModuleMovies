package com.morfly.sample.images

import androidx.compose.runtime.compositionLocalOf


/**
 * Contains all DI dependencies that 'images' feature provides.
 */
interface ImagesProvider


val LocalImagesProvider = compositionLocalOf<ImagesProvider> {
    error("No images provider found!")
}