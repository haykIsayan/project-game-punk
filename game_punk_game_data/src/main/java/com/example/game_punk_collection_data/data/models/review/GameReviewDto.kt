package com.example.game_punk_collection_data.data.models.review

import com.example.game_punk_collection_data.data.models.game.GameExperienceModel
import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_domain.domain.entity.GameReviewEntity

data class GameReviewDto(
    override val id: String = "",
    override val userId: String = "",
    override val gameId: String = "",
    override val userScore: Int = 0,
    override val userReview: String = "",
    override val timestamp: String = ""
) : GameReviewEntity {

    fun toMap() = hashMapOf(
        "id" to id,
        "userId" to userId,
        "gameId" to gameId,
        "userScore" to userScore,
        "userReview" to userReview,
        "timestamp" to timestamp
    )


    companion object {
        const val GAME_REVIEW_DB_COLLECTION_NAME = "game_reviews"
    }

    override fun withUserReview(userReview: String): GameReviewEntity {
        return copy(userReview = userReview)
    }

    override fun withUserScore(userScore: Int): GameReviewEntity {
        return copy(userScore = userScore)
    }
}
