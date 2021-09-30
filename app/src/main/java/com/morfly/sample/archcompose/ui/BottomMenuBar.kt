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

package com.morfly.sample.archcompose.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.morfly.sample.common.Destinations
import com.morfly.sample.common.find
import com.morfly.sample.common.ui.GlowingMenuIcon
import com.morfly.sample.common.ui.glow
import com.morfly.sample.common.ui.theme.AppBlack
import com.morfly.sample.images.ImagesEntry
import com.morfly.sample.profile.ProfileFeatureEntry


@Composable
fun BottomMenuBar(
    navController: NavController,
    destinations: Destinations
) {
    BottomNavigationLayout {
        GlowingMenuIcon(
            isGlowing = true,
            glowingIcon = Icons.Rounded.Home,
            idleIcon = Icons.Outlined.Home,
            modifier = Modifier
                .clickable(
                    indication = rememberRipple(bounded = false),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    val route = destinations.find<ImagesEntry>().featureRoute
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
        )
        Box(contentAlignment = Alignment.Center) {
            val context = LocalContext.current
            GlowingMenuIcon(
                isGlowing = false,
                glowingIcon = Icons.Rounded.Add,
                idleIcon = Icons.Outlined.Add,
                modifier = Modifier.clickable {
                    Toast.makeText(context, "Not implemented.", Toast.LENGTH_SHORT).show()
                }
            )
            Box(
                Modifier
                    .aspectRatio(1f)
                    .padding(15.dp)
                    .fillMaxSize()
                    .border(
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        shape = RoundedCornerShape(percent = 50)
                    )
            )
        }
        GlowingMenuIcon(
            isGlowing = false,
            glowingIcon = Icons.Rounded.Person,
            idleIcon = Icons.Outlined.Person,
            modifier = Modifier.clickable(
                indication = rememberRipple(bounded = false),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                val route = destinations
                    .find<ProfileFeatureEntry>()
                    .myProfileDestination()
                navController.navigate(route) {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
private inline fun BottomNavigationLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(70.dp)
            .glow(AppBlack, radius = 20.dp, alpha = 0.9f, offsetY = 10.dp)
            .clip(RoundedCornerShape(topStartPercent = 40, topEndPercent = 40))
            .background(AppBlack),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}