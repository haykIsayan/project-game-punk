package com.example.game_punk_collection_data.data.news

import retrofit2.http.GET
import retrofit2.http.Query

interface GamingNewsApi {
    @GET("everything")
    suspend fun getGamingNews(
//        @Query("q") q: String,
        @Query("sources") sources: String,
        @Query("languages") languages: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
//        @Query("searchIn") searchIn: String,
//        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): GamingNewsResponse

    @GET("top-headlines")
    suspend fun getTopGamingNews(
        @Query("country") country: String,
//        @Query("sources") sources: String,
        @Query("country") category: String,
//        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): GamingNewsResponse
}