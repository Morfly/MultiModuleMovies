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