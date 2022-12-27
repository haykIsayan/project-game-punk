package com.example.project_game_punk.data.game.idgb.api

import com.example.project_game_punk.data.game.rawg.models.TwitchApiResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface TwitchApi {
    @GET("games/top")
    suspend fun getTopGamesOnTwitch(
        @HeaderMap headers: Map<String, String>,
    ): TwitchApiResponse
}