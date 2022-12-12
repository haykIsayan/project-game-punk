package com.example.project_game_punk.domain.models

import com.example.project_game_punk.domain.entity.GameMetaQueryModel

data class GameQueryModel(
    val query: String = "",
    val sort: GameSort = GameSort.none,
    val dateRangeStart: String = "",
    val dateRangeEnd: String = "",
    val isHighestRated: Boolean = false,
    val gameMetaQuery: GameMetaQueryModel = GameMetaQueryModel(),
    val ids: List<String> = emptyList()
)
