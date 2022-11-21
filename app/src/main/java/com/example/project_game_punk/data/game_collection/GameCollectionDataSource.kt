package com.example.project_game_punk.data.game_collection

import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.models.GameCollectionModel

class GameCollectionDataSource(
    private val gameCollectionDatabase: GameCollectionDatabase,
    private val gameRepository: GameRepository,
): GameCollectionRepository {
    override suspend fun updateGameCollection(gameCollection: GameCollectionModel) {
        gameCollectionDatabase.gameCollectionDao().updateGameCollection(gameCollection)
    }

    override suspend fun createGameCollection(gameCollection: GameCollectionModel) {
        gameCollectionDatabase.gameCollectionDao().createGameCollection(gameCollection)
    }

    override suspend fun getGameCollections(): List<GameCollectionModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameCollection(id: String): GameCollectionModel {
        return gameCollectionDatabase.gameCollectionDao().getGameCollection(id)
    }
}