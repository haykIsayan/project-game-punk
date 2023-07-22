package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameCollectionEntity

interface GameCollectionRepository {
    suspend fun updateGameCollection(gameCollection: GameCollectionEntity)

    suspend fun createGameCollection(gameCollection: GameCollectionEntity)

    suspend fun getGameCollections(): List<GameCollectionEntity>

    suspend fun getGameCollection(id: String): GameCollectionEntity?
}