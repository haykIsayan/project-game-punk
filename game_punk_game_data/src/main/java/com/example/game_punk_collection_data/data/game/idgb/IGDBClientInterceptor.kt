package com.example.game_punk_collection_data.data.game.idgb

import com.google.common.util.concurrent.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class IGDBClientInterceptor(
    val scope: CoroutineScope = GlobalScope
): Interceptor {

//    private val limiter = RateLimiter.create(4.0)


    private val requestQueue = ArrayDeque<Request>()

    override fun intercept(chain: Interceptor.Chain): Response {

//        limiter.acquire(4)

        val request = chain.request()
        val url = request.url()
        val newUrl = url.newBuilder()
            .build()
        val newRequestBuilder = request
            .newBuilder()
            .header(
                "Cache-Control",
                "public, max-age="
            )
            .url(newUrl)
        val newRequest = newRequestBuilder.build()
        // add to request queue


//        if (requestQueue.size >= 4) {
//
//        }
//
//        requestQueue.addLast(newRequest)





        return chain.proceed(newRequest)
    }


//    private fun enqueueIGDBRequest(request: Request): Request {
//        if (requestQueue.size >= 4)
//    }

}
