package com.example.project_game_punk.domain.interactors.game_collection.tracking

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.entity.GameCollectionFactory
import com.example.project_game_punk.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.GetGameCollectionInteractor

class GetTrackedGamesInteractor(
    private val gameCollectionFactory: GameCollectionFactory,
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
    private val createGameCollectionInteractor: CreateGameCollectionInteractor,
) {
    suspend fun execute(): GameCollectionEntity {
        return getMainCollection() ?: createMainCollection()
    }

    private suspend fun getMainCollection(): GameCollectionEntity? {
        return getGameCollectionInteractor.execute(id = "main")
    }

    private suspend fun createMainCollection(): GameCollectionEntity {
        return createGameCollectionInteractor.execute(
            gameCollectionFactory.createGameCollection(
                id = "main",
                name = "",
                games = emptyList()
            )
        )
    }
}