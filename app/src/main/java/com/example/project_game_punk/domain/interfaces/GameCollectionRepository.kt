package com.example.project_game_punk.domain.interfaces

import com.example.project_game_punk.domain.models.GameCollectionModel

interface GameCollectionRepository {
    suspend fun updateGameCollection(gameCollection: GameCollectionModel)

    suspend fun createGameCollection(gameCollection: GameCollectionModel)

    suspend fun getGameCollections(): List<GameCollectionModel>

    suspend fun getGameCollection(id: String): GameCollectionModel?
}