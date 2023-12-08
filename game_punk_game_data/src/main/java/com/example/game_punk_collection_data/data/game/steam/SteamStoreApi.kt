package com.example.game_punk_collection_data.data.game.steam

import retrofit2.http.GET
import retrofit2.http.Path

interface SteamStoreApi {
    @GET("appdetails/")
    suspend fun getAppDetails(@Path("appids") appId: String)
}