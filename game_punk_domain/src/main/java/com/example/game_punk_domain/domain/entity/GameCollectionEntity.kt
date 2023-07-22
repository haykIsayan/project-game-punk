package com.example.game_punk_domain.domain.entity

interface GameCollectionEntity {
    val id: String?
    val name: String?
    val games: List<GameEntity>

    fun withGames(games: List<GameEntity>): GameCollectionEntity
}