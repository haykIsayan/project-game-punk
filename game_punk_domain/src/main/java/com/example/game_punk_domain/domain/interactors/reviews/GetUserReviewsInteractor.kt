package com.example.game_punk_domain.domain.interactors.reviews

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interfaces.ReviewRepository
import com.example.game_punk_domain.domain.models.ReviewQueryModel

class GetUserReviewsInteractor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun execute(userId: String): List<GameReviewEntity> {
        val query = ReviewQueryModel(
            listOf(userId),
        )
        return reviewRepository.getReviews(query)
    }
}