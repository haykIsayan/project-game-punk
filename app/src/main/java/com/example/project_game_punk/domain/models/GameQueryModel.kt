package com.example.project_game_punk.domain.models

data class GameQueryModel(
    val query: String = "",
    val sort: GameSort = GameSort.trending,
    val dateRangeStart: String = "",
    val dateRangeEnd: String = "",
    val isHighestRated: Boolean = false,
    val ids: List<String> = emptyList()
)
