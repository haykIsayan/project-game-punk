package com.example.game_punk_collection_data.data.game.rawg

import com.example.game_punk_collection_data.data.models.game.GameRAWGAchievementsDto
import com.example.game_punk_collection_data.data.models.game.GameRAWGModel
import com.example.game_punk_collection_data.data.models.game.GameRedditPostDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApi {
    @GET("games")
    suspend fun getGames(
        @Query("search") search: String? = null,
        @Query("dates") dates: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("metacritic") metacritic: String? = null,
        @Query("added") added: String? = null,
        @Query("search_precise") searchPrecise: Boolean = true,
        @Query("exclude_additions") excludeAdditions: Boolean = true,
        @Query("search_exact") searchExact: Boolean = true,
        @Query("genres") genres: String? = null,
        @Query("ids") ids: String? = null,
        @Query("slugs") slugs: String? = null
    ): RawgGameResponseModel

    @GET("games/{id}")
    suspend fun getGame(
        @Path("id") id: String,
//        @Query("search") search: String? = null,
//        @Query("dates") dates: String? = null,
//        @Query("ordering") ordering: String? = null,
//        @Query("metacritic") metacritic: String? = null,
//        @Query("added") added: String? = null,
//        @Query("search_precise") searchPrecise: Boolean = true,
//        @Query("exclude_additions") excludeAdditions: Boolean = true,
//        @Query("search_exact") searchExact: Boolean = true,
//        @Query("genres") genres: String? = null,
//        @Query("ids") ids: String? = null,
//        @Query("slugs") slugs: String? = null
    ): GameRAWGModel


//    /games/{id}/achievements


    @GET("games/{id}/achievements")
    suspend fun getAchievements(
        @Path("id") slug: String?,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): RawgAchievementsResponseModel


//    https://api.rawg.io/api/games/{id}/reddit

    @GET("games/{id}/reddit")
    suspend fun getRecentRedditPosts(
        @Path("id") slug: String?,
    ): RawgRedditPostResponseModel

}