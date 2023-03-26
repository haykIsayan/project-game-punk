package com.example.project_game_punk.domain.entity

data class GameMetaQueryModel(
    val cover: Boolean = true,
    val banner: Boolean = false,
    val platforms: Boolean = false,
    val screenshots: Boolean = false,
    val synopsis: Boolean = false,
)