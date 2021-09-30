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

package com.morfly.sample.data.impl.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class PixabayRequestFieldsSetter @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "20158920-274a1e484842c9ef690adf8e0"
    }
}