package com.example.game_punk_domain.domain.models

import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.entity.GamePlatformEntity

data class GameQueryModel(
    val query: String = "",
    val filter: GameFilter = GameFilter.none,
    val sort: GameSort = GameSort.none,
    val dateRangeStart: String = "",
    val dateRangeEnd: String = "",
    val gameMetaQuery: GameMetaQueryModel = GameMetaQueryModel(),
    val platforms: List<GamePlatformEntity> = emptyList(),
    val genres: List<GameGenreEntity> = emptyList(),
    val ids: List<String> = emptyList()
)
