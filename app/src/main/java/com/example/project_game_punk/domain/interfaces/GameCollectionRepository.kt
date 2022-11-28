package com.example.project_game_punk.domain.interfaces

import com.example.project_game_punk.domain.entity.GameCollectionEntity

interface GameCollectionRepository {
    suspend fun updateGameCollection(gameCollection: GameCollectionEntity)

    suspend fun createGameCollection(gameCollection: GameCollectionEntity)

    suspend fun getGameCollections(): List<GameCollectionEntity>

    suspend fun getGameCollection(id: String): GameCollectionEntity?
}