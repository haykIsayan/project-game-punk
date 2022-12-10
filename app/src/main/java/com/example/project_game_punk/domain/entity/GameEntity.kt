package com.example.project_game_punk.domain.entity

interface GameEntity {
    val id: String?
    val name: String?
    val backgroundImage: String?
    val banner: String?
    val banners: List<String>?
    val gameArtworks: List<String>?
    val numAdded: Int
    val metaCriticScore: Int
    val isAdded: Boolean
    val gameProgress: GameProgress

    fun updateGameProgress(gameProgress: GameProgress): GameEntity
}
