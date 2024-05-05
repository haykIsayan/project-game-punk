package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.models.ReviewQueryModel

interface ReviewRepository {
    suspend fun getReviews(query: ReviewQueryModel): List<GameReviewEntity>

    suspend fun createReview(gameReview: GameReviewEntity): GameReviewEntity

    suspend fun updateReview(gameReview: GameReviewEntity)
}





