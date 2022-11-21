package com.example.project_game_punk.domain.interactors.game_collection.tracking

import com.example.project_game_punk.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.project_game_punk.domain.models.GameCollectionModel

class GetTrackedGamesInteractor(
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
    private val createGameCollectionInteractor: CreateGameCollectionInteractor,
) {
    suspend fun execute(): GameCollectionModel {
        return getGameCollectionInteractor.execute(id = "main")
            ?: createGameCollectionInteractor.execute(
                GameCollectionModel(
                    uuid = 0,
                    id = "main",
                    name = null
                )
            )
    }
}