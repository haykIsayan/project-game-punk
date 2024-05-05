package com.example.game_punk_collection_data.data.game.twitch

import com.example.game_punk_collection_data.data.models.game.TwitchApiResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface TwitchApi {
    @GET("games/top")
    suspend fun getTopGamesOnTwitch(
        @HeaderMap headers: Map<String, String>,
    ): TwitchApiResponse
}