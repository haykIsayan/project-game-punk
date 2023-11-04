package com.example.game_punk_collection_data.data.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity

data class SteamNewsResponse(
    val appnews: SteamAppNewsModel? = null
)

data class SteamAppNewsModel(
    val newsitems: List<SteamNewsItemModel>? = null
)

data class SteamNewsItemModel(
    val contents: String? = null,
    override val title: String,
): GameNewsEntity {
    override val description: String
        get() = contents ?: ""
}
