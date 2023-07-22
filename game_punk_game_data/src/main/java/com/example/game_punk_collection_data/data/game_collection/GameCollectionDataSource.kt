package com.example.game_punk_collection_data.data.game_collection

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GameCollectionDataSource(
    private val gameCollectionDatabase: GameCollectionDatabase,
    private val gameRepository: GameRepository,
): GameCollectionRepository {
    override suspend fun updateGameCollection(gameCollection: GameCollectionEntity) {
        gameCollectionDatabase.gameCollectionDao().updateGameCollection(gameCollection as GameCollectionModel)
    }

    override suspend fun createGameCollection(gameCollection: GameCollectionEntity) {
        gameCollectionDatabase.gameCollectionDao().createGameCollection(gameCollection as GameCollectionModel)
    }

    override suspend fun getGameCollections(): List<GameCollectionEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameCollection(id: String): GameCollectionEntity {
        return gameCollectionDatabase.gameCollectionDao().getGameCollection(id)
    }
}