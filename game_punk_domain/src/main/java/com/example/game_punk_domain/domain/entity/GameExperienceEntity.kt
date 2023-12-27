package com.example.game_punk_domain.domain.entity

enum class GameProgressStatus {
    following,
    notFollowing,
    playing,
    stopped,
    finished,
    replaying,
    excited
}

interface GameExperienceEntity {
    val userId: String
    val gameId: String
    val userScore: Float
    val isFavorite: Boolean?
    val storeId: String?
    val platformId: String?
    val gameProgressStatus: GameProgressStatus?
    val userReview: String?

    fun updateGameProgressStatus(
        gameProgressStatus: GameProgressStatus
    ): GameExperienceEntity

    fun updateIsFavorite(
        isFavorite: Boolean
    ): GameExperienceEntity

    fun updateStoreId(storeId: String): GameExperienceEntity

    fun updatePlatformId(platformId: String): GameExperienceEntity
}