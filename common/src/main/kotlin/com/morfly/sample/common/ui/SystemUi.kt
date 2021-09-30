/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morfly.sample.common.ui

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