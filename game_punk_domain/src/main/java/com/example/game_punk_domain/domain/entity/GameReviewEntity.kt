package com.example.game_punk_domain.domain.entity

interface GameReviewEntity {
    val id: String
    val userId: String
    val gameId: String
    val userScore: Int
    val userReview: String
    val timestamp: String

    fun withUserReview(userReview: String): GameReviewEntity

    fun withUserScore(userScore: Int): GameReviewEntity
}