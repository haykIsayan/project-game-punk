package com.example.game_punk_collection_data.data.review

import com.example.game_punk_collection_data.data.models.review.GameReviewDto
import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interactors.reviews.GameReviewFactory

class GameReviewFactoryImpl: GameReviewFactory {
    override fun create(userId: String, gameId: String): GameReviewEntity {
        return GameReviewDto(
            id = "",
            userId = userId,
            gameId = gameId,
            userScore = 0,
            userReview = "",
            timestamp = ""
        )
    }
}