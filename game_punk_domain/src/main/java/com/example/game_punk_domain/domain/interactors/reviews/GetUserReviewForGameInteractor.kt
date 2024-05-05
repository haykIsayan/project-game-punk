package com.example.game_punk_domain.domain.interactors.reviews

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interfaces.ReviewRepository
import com.example.game_punk_domain.domain.models.ReviewQueryModel

class GetUserReviewForGameInteractor(
    private val gameReviewFactory: GameReviewFactory,
    private val reviewRepository: ReviewRepository
) {
    suspend fun execute(
        userId: String,
        gameId: String
    ): GameReviewEntity? {
        val gameReviews = reviewRepository.getReviews(
            ReviewQueryModel(
                userIds = listOf(userId),
                gameId = gameId
            )
        )
        return gameReviews.firstOrNull()
//            ?: gameReviewFactory.create(
//            userId,
//            gameId
//        )
    }
}