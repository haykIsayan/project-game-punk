package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_domain.domain.entity.GameAgeRatingEntity

data class GameAgeRatingModel(
    val rating_cover_url: String,
    val synopsis: String
): GameAgeRatingEntity {
    override val logo: String
        get() = rating_cover_url
    override val description: String
        get() = synopsis
}