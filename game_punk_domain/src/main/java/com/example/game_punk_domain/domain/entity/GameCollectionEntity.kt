package com.example.game_punk_domain.domain.entity

interface GameCollectionEntity {
    val userId: String?
    val id: String?
    val name: String?
    val games: List<GameEntity>

    fun withGames(games: List<GameEntity>): GameCollectionEntity
}