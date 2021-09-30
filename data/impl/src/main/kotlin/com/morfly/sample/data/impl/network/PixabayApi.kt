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

@file:Suppress("unused")

package com.morfly.sample.data.impl.network

import com.morfly.sample.data.impl.network.model.PixabayImageResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayApi {

    companion object {
        const val BASE_URL = "https://pixabay.com/api/"

        const val MIN_PER_PAGE_VALUE = 3
    }

    @GET(".")
    suspend fun images(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("lang") lang: String? = null,
        @Query("image_type") imageType: String = "photo",
        @Query("orientation") orientation: String = "all",
        @Query("category") category: String? = null,
        @Query("min_width") minWidth: Int? = null,
        @Query("min_height") minHeight: Int? = null,
        @Query("colors") colors: String? = null,
        @Query("editors_choice") editorsChoice: Boolean = true,
        @Query("safesearch") safeSearch: Boolean = true,
        @Query("order") order: String = "popular"
    ): PixabayImageResponse

    @GET(".")
    suspend fun image(@Query("id") id: String): PixabayImageResponse
}