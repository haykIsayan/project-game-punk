package com.example.project_game_punk.domain.interactors.game_collection.tracking

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.entity.GameCollectionFactory
import com.example.project_game_punk.domain.entity.GameMetaQueryModel
import com.example.project_game_punk.domain.interactors.game.ApplyGameMetaInteractor
import com.example.project_game_punk.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.GetGameCollectionInteractor

class GetTrackedGamesInteractor(
    private val applyGameMetaInteractor: ApplyGameMetaInteractor,
    private val gameCollectionFactory: GameCollectionFactory,
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
    private val createGameCollectionInteractor: CreateGameCollectionInteractor,
) {
    suspend fun execute(): GameCollectionEntity {
        return getMainCollection() ?: createMainCollection()
    }

    private suspend fun getMainCollection(): GameCollectionEntity? {
        val mainCollection = getGameCollectionInteractor.execute(id = "main")
        mainCollection?.games?.let { games ->
            val updatedGames = applyGameMetaInteractor.execute(
                games,
                GameMetaQueryModel(banner = true)
            )
            return mainCollection.withGames(updatedGames)
        }
        return mainCollection
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