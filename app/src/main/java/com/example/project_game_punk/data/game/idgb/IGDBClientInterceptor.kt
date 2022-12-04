package com.example.project_game_punk.data.game.idgb

import com.example.project_game_punk.data.game.rawg.RawgClientInterceptor
import okhttp3.Interceptor
import okhttp3.Response

class IGDBClientInterceptor(
    private val authToken: String,
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val newUrl = url.newBuilder()
            .build()
        val newRequestBuilder = request.newBuilder()
            .url(newUrl)
        val newRequest = newRequestBuilder.build()
        return chain.proceed(newRequest)
    }
}