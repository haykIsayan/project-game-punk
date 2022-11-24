package com.example.project_game_punk.data.game

import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApi {
    @GET("games")
    suspend fun getGames(
        @Query("search") search: String,
        @Query("dates") dates: String,
        @Query("ordering") ordering: String? = null,
        @Query("metacritic") metacritic: String,
        @Query("added") added: String? = null,
        @Query("search_precise") searchPrecise: Boolean = true,
        @Query("exclude_additions") excludeAdditions: Boolean = true,
        @Query("search_exact") searchExact: Boolean = true,
        @Query("genres") genres: String? = null,
        @Query("ids") ids: String? = null
    ): RawgGameResponseModel
}