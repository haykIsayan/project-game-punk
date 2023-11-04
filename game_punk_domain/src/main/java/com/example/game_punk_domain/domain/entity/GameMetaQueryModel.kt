package com.example.game_punk_domain.domain.entity

data class GameMetaQueryModel(
    val cover: Boolean = true,
    val banner: Boolean = false,
    val platforms: Boolean = false,
    val screenshots: Boolean = false,
    val synopsis: Boolean = false,
    val steamId: Boolean = false
)