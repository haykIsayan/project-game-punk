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
    val isFavorite: Boolean?
    val store: GameStoreEntity?
    val platform: GamePlatformEntity?
    val gameProgressStatus: GameProgressStatus?

    fun updateGameProgressStatus(
        gameProgressStatus: GameProgressStatus
    ): GameExperienceEntity

    fun updateIsFavorite(
        isFavorite: Boolean
    ): GameExperienceEntity
}