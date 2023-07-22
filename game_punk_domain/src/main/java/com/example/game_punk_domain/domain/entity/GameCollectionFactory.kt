package com.example.game_punk_domain.domain.entity

interface GameCollectionFactory {
    fun createGameCollection(
        id: String,
        name: String,
        games: List<GameEntity>
    ): GameCollectionEntity
}