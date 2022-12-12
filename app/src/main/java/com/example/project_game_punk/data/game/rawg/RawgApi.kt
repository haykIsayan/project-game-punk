package com.example.project_game_punk.data.game.rawg

import retrofit2.http.GET
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
}