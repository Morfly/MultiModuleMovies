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

package com.morfly.sample.profile.impl.userprofile.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.morfly.sample.profile.impl.ProfileEntryImpl
import com.morfly.sample.profile.impl.userprofile.UserProfileViewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel,
) {
    val images by viewModel.images().collectAsState(initial = emptyList())
    val avatarUrl by viewModel.avatarUrl.collectAsState()
    val username by viewModel.username.collectAsState()

    Column {
        Box(Modifier.fillMaxWidth().height(66.dp), contentAlignment = Alignment.Center) {
            if (viewModel.showSettingsButton)
                Icon(
                    Icons.Default.Settings,
                    contentDescription = null,
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(22.dp)
                        .clickable {
                            navController.navigate(ProfileEntryImpl.InternalRoutes.SETTINGS)
                        }
                )
            Text(text = viewModel.screenTitle, style = MaterialTheme.typography.subtitle1)
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            // Avatar
            if (avatarUrl != null) {
                Image(
                    painter = rememberImagePainter(avatarUrl),
                    contentDescription = null,
                    Modifier
                        .padding(top = 20.dp)
                        .size(105.dp)
                        .clip(RoundedCornerShape(50))
                )
            }
            Spacer(Modifier.height(10.dp))

            // Username
            Text(
                text = username ?: "",
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(Modifier.height(30.dp))

            // Images
            LazyVerticalGrid(cells = GridCells.Fixed(count = 3)) {
                items(images.size) { index ->
                    GridItem(imageUrl = images[index].url)
                }
            }
        }
    }
}