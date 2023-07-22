package com.example.game_punk_collection_data.data.game.idgb.api

import com.example.game_punk_collection_data.data.game.rawg.models.GameCoverModel
import com.example.game_punk_collection_data.data.game.rawg.models.GameModel
import com.example.game_punk_collection_data.data.game.rawg.models.PlatformLogoModel
import com.example.game_punk_collection_data.data.game.rawg.models.PlatformModel
import retrofit2.http.Body
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

    @POST("platforms")
    suspend fun getPlatforms(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<PlatformModel>

    @POST("platform_logos")
    suspend fun getPlatformLogos(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<PlatformLogoModel>
}