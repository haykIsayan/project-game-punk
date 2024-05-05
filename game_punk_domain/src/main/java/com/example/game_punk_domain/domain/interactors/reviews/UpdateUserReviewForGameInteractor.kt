package com.example.game_punk_domain.domain.interactors.reviews

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interfaces.ReviewRepository
import com.example.game_punk_domain.domain.models.ReviewQueryModel

class UpdateUserReviewForGameInteractor(
    private val repository: ReviewRepository
) {
    suspend fun execute(gameReview: GameReviewEntity): GameReviewEntity {
        if (
            gameReview.id.isEmpty() &&
            repository.getReviews(
                ReviewQueryModel(
                    userIds = listOf(gameReview.userId),
                    gameId = gameReview.gameId
                )
            ).isEmpty()
        ) {
            repository.createReview(gameReview)
        }
        repository.updateReview(gameReview)
        return gameReview
    }
}