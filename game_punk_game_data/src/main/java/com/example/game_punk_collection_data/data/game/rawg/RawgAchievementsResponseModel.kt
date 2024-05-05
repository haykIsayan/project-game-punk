package com.example.game_punk_collection_data.data.game.rawg

import com.example.game_punk_collection_data.data.models.game.GameRAWGAchievementsDto
import com.example.game_punk_collection_data.data.models.game.GameRAWGModel
import com.example.game_punk_domain.domain.entity.GameAchievementEntity

data class RawgAchievementsResponseModel(
    val results: List<GameRAWGAchievementsDto>,
    val next: String? = null
)