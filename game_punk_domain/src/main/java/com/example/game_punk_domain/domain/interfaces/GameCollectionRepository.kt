package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameCollectionEntity

interface GameCollectionRepository {
    suspend fun updateGameCollection(gameCollection: GameCollectionEntity)

    suspend fun createGameCollection(gameCollection: GameCollectionEntity): GameCollectionEntity


    suspend fun deleteGameCollection(gameCollection: GameCollectionEntity)

    suspend fun getGameCollections(userId: String): List<GameCollectionEntity>

    suspend fun getGameCollection(
        id: String,
        userId: String
    ): GameCollectionEntity?
}