package com.example.game_punk_collection_data.data.game.idgb.api

import com.example.game_punk_collection_data.data.models.*
import com.example.game_punk_collection_data.data.models.game.*
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

    @POST("websites")
    suspend fun getWebsites(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<WebsiteModel>

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

    @POST("age_ratings")
    suspend fun getAgeRatings(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameAgeRatingModel>

    @POST("external_games")
    suspend fun getExternalGames(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameExternalModel>

    @POST("release_dates")
    suspend fun getReleaseDates(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameReleaseModel>

    @POST("involved_companies")
    suspend fun getInvolvedCompanies(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<InvolvedCompanyModel>

    @POST("companies")
    suspend fun getCompanies(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<CompanyModel>

    @POST("company_logos")
    suspend fun getCompanyLogos(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GameCoverModel>


    @POST("genres")
    suspend fun getGenres(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<GenreModel>

    @POST("platform_logos")
    suspend fun getPlatformLogos(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<PlatformLogoModel>



//    https://api.igdb.com/v4/game_videos

    @POST("game_videos")
    suspend fun getVideos(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
    ): List<VideoModel>


//    https://api.igdb.com/v4/keywords
    @POST("keywords")
    suspend fun getKeywords(
        @HeaderMap headers: Map<String, String>,
        @Body fields: String
): List<KeywordModel>

}