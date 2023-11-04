package com.example.game_punk_collection_data.data.news

import retrofit2.http.GET
import retrofit2.http.Query


//http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=440&count=3&maxlength=300&format=json


/**
 * GetNewsForApp returns the latest of a game specified by its appID.

Example URL: http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=440&count=3&maxlength=300&format=json

Arguments
appid
AppID of the game you want the news of.
count
How many news enties you want to get returned.
maxlength
Maximum length of each news entry.
format
Output format. json (default), xml or vdf.



Result layout
An appnews object containing:

appid, the AppID of the game you want news of
newsitems, an array of news item information:
An ID, title and url.
A shortened excerpt of the contents (to maxlength characters), terminated by "..." if longer than maxlength.
A comma-separated string of labels and UNIX timestamp.
 */







interface SteamNewsApi {
    @GET("ISteamNews/GetNewsForApp/v0002/")
    suspend fun getNewsForGame(@Query("appid") appId: Int, @Query("count") count: Int = 9): SteamNewsResponse
}