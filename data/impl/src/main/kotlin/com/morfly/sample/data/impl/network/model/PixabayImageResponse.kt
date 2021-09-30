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

@file:Suppress("SpellCheckingInspection")

package com.morfly.sample.data.impl.network.model

import com.squareup.moshi.Json


data class PixabayImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<PixabayImage>
)

data class PixabayImage(
    val id: String,
    val type: String,
    val tags: String,
    @Json(name = "previewURL") val previewUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    @Json(name = "webformatURL") val webformatUrl: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    @Json(name = "largeImageURL") val largeImageUrl: String,
    @Json(name = "fullHDURL") val fullHdUrl: String?,
    @Json(name = "imageURL") val imageUrl: String?,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Long,
    val views: Int,
    val downloads: Int,
    val favorites: Int?,
    val likes: Int,
    val comments: Int,
    @Json(name = "user_id") val userId: String,
    val user: String,
    @Json(name = "userImageURL") val userImageUrl: String
)