package com.example.project_game_punk.data.game_collection

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.data.game.rawg.models.GameCollectionModel

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