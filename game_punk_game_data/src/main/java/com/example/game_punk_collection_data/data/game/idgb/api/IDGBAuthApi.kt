package com.example.game_punk_collection_data.data.game.idgb.api

import com.example.game_punk_collection_data.data.game.idgb.IDGBAuthModel
import retrofit2.http.POST
import retrofit2.http.Query

interface IDGBAuthApi {


//    ?client_id=abcdefg12345&client_secret=hijklmn67890&grant_type=client_credentials
    @POST("token")
    suspend fun authenticate(
    @Query("client_id") clientId: String,
    @Query("client_secret") clientSecret: String,
    @Query("grant_type") grantType: String = "client_credentials"
    ): IDGBAuthModel
}