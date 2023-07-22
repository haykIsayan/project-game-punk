package com.example.game_punk_domain.domain.entity

interface GameEntity {
    val id: String?
    val name: String?
    val description: String?
    val backgroundImage: String?
    val banner: String?
    val banners: List<String>?
    val gameArtworks: List<String>?
    val numAdded: Int
    val metaCriticScore: Int
    val isAdded: Boolean
    val gameProgress: GameProgress
    val gamePlatforms: List<GamePlatformEntity>?

    fun updateGameProgress(gameProgress: GameProgress): GameEntity
}
