package com.example.game_punk_collection_data.data.game.rawg

import com.example.game_punk_collection_data.data.models.game.GameRAWGAchievementsDto

data class RawgAchievementsResponseModel(
    val results: List<GameRAWGAchievementsDto>,
    val next: String? = null
)