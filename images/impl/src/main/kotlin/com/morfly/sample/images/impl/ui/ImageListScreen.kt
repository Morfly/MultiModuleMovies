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

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.morfly.sample.images.impl.ImagesViewModel


@Composable
fun ImageListScreen(
    viewModel: ImagesViewModel,
    onUserSelected: (userId: String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column {
        SearchField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.updateSearchQuery(searchQuery)
            },
            onClick = {
                viewModel.updateSearchQuery(searchQuery)
            }
        )
        ImageList(viewModel, onUserSelected)
    }
}