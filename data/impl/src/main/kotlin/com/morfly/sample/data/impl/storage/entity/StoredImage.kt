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

package com.morfly.sample.data.impl.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.morfly.sample.common.domain.Image
import com.morfly.sample.common.domain.User


@Entity(tableName = "images")
data class StoredImage(
    @PrimaryKey override val id: String,
    override val query: String,
    override val url: String,
    @ColumnInfo(name = "preview_url") override val previewUrl: String,
    override val tags: String,
    override val views: Int,
    override val downloads: Int,
    override val favorites: Int,
    override val likes: Int,
    override val comments: Int,
    override val timestamp: Long,
    @Embedded(prefix = "user_") override val user: StoredUser
) : Image