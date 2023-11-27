package com.example.game_punk_domain.domain.entity

data class GameMetaQueryModel(
    val cover: Boolean = true,
    val banner: Boolean = false,
    val platforms: Boolean = false,
    val genres: Boolean = false,
    val screenshots: Boolean = false,
    val synopsis: Boolean = false,
    val ageRating: Boolean = false,
    val score: Boolean = false,
    val similarGames: Boolean = false,
    val steamId: Boolean = false
)