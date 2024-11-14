package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_domain.domain.entity.game.GameAchievementEntity

data class GameRAWGAchievementsDto(
    override val id: Int,
    override val name: String,
    override val image: String,
//    override val isCompleted: Boolean = false

//    "id": 0,
//    "name": "string",
//"description": "string",
//"image": "http://example.com",
//"percent": "string"
): GameAchievementEntity
