package com.example.project_game_punk.data.game.idgb.api

import com.example.project_game_punk.data.game.rawg.models.GameCoverModel
import com.example.project_game_punk.data.game.rawg.models.GameModel
import com.example.project_game_punk.domain.entity.GameEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IDGBApi {

    @POST("games")
    suspend fun getGames(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String,
    ): List<GameModel>

    @POST("covers")
    suspend fun getCovers(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameCoverModel>

    @POST("screenshots")
    suspend fun getScreenshots(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameCoverModel>

    @POST("artworks")
    suspend fun getArtworks(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameCoverModel>
}