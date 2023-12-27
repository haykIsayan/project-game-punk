package com.example.game_punk_collection_data.data.game.idgb

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class IGDBClientInterceptor(
    val scope: CoroutineScope = GlobalScope
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        val newUrl = url.newBuilder()
            .build()
        val newRequestBuilder = request.newBuilder()
            .url(newUrl)
        val newRequest = newRequestBuilder.build()

//        Log.d("Haykk", "in intercepting")

        return try {
            val response = chain.proceed(newRequest)

            Log.d("Haykk", "in intercepting response $response")

            if (response.code() == 429) {
//                scope.launch {
//                    delay(5000)
//                    Log.d("Haykk", "Bruuuuh too many requests bruuuh")
//                    response.close()
//                    chain.proceed(newRequest)
//
//
//                    chain.call().
//
//
//                }.

                response.close()
                chain.proceed(newRequest)
            } else {
                response
            }

        } catch (e: Exception) {
            Log.d("Haykk", "in interceptor $e")
            throw e
        }
    }



}