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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(30),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(50.dp),
        elevation = 6.dp,
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                Modifier.weight(1f)
            )
            Box(Modifier.padding(5.dp)) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable { onClick() }
                        .padding(5.dp)
                )
            }
        }
    }
}