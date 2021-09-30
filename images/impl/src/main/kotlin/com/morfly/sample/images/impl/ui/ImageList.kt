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

package com.morfly.sample.images.impl.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.morfly.sample.common.ui.theme.AppBlue
import com.morfly.sample.images.impl.ImagesViewModel


@Composable
inline fun ImageList(
    viewModel: ImagesViewModel,
    crossinline onUserSelected: (userId: String) -> Unit
) {
    val images = viewModel.images.collectAsLazyPagingItems()
    val searchSuggestion = remember { viewModel.searchSuggestion }

    Box {
        LazyColumn {
            item { Spacer(Modifier.height(15.dp)) }
            items(images.itemCount) { index ->
                images[index]?.let { image ->
                    ImageItem(image, onUserSelected)
                }
            }
            item { Spacer(Modifier.height(70.dp)) }
        }
        if (images.itemCount == 0) Empty(searchSuggestion)
        Loading(loadState = images.loadState)
    }
}

@Composable
fun Loading(loadState: CombinedLoadStates) = with(loadState) {
    when (LoadState.Loading) {
        refresh -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    Modifier.size(50.dp),
                    color = AppBlue,
                    strokeWidth = 5.dp
                )
            }
        }
    }
}

@Composable
fun Empty(searchSuggestion: String) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Try searching \"$searchSuggestion\"",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}