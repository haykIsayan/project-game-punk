package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_domain.domain.entity.GameSteamReviewEntity

data class GameSteamReviewDto(
    val gameSteamReviewAuthor: GameSteamReviewAuthorDto,
    val review: String
) : GameSteamReviewEntity {
    override val steamId: String
        get() = gameSteamReviewAuthor.steamid
    override val text: String
        get() = review
}

data class GameSteamReviewAuthorDto(
    val steamid: String
)