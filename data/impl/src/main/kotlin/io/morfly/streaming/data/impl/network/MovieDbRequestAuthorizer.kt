package io.morfly.streaming.data.impl.network

import io.morfly.streaming.data.impl.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class MovieDbRequestAuthorizer @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}