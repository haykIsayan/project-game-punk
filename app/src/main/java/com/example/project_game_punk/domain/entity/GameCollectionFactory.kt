package com.example.project_game_punk.domain.entity

interface GameCollectionFactory {
    fun createGameCollection(
        id: String,
        name: String,
        games: List<GameEntity>
    ): GameCollectionEntity
}