package com.example.game_punk_domain.domain.interactors.game_collection.tracking

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameCollectionFactory
import com.example.game_punk_domain.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor

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