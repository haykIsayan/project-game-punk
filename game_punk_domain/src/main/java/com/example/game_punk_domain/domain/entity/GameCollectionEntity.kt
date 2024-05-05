package com.example.game_punk_domain.domain.entity

interface GameCollectionEntity {
    val userId: String?
    val id: String?
    val name: String?
    val games: List<GameEntity>


    fun withName(name: String): GameCollectionEntity

    fun withGames(games: List<GameEntity>): GameCollectionEntity
}