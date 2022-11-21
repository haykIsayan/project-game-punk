package com.example.project_game_punk.domain

import com.example.project_game_punk.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.project_game_punk.domain.models.GameCollectionModel
import com.example.project_game_punk.domain.models.GameModel

class TrackedGamesCache(
    private val getTrackedGamesInteractor: GetTrackedGamesInteractor,
) {

    private var gameCollection: GameCollectionModel? = null

    fun updateCache(updatedMainGameCollection: GameCollectionModel) {
        this.gameCollection = updatedMainGameCollection
    }

    suspend fun getMainGameCollection(): GameCollectionModel {
        if (gameCollection != null) return gameCollection as GameCollectionModel
        return loadMainGameCollection()
    }

    suspend fun applyCache(games: List<GameModel>): List<GameModel> {
        val mainGameCollection = getMainGameCollection()
        val updatedGames = games.map { game ->
            val cachedGame = mainGameCollection.games.find { it.id == game.id }
            if (cachedGame != null) game.copy(isAdded = true, gameProgressStatus = cachedGame.gameProgressStatus) else game
        }
        return updatedGames
    }

    suspend fun applyCache(game: GameModel): GameModel {
        val mainGameCollection = getMainGameCollection()
        val trackedGames = mainGameCollection.games
        val isAdded = trackedGames.find { it.id == game.id } != null
        return game.copy(isAdded = isAdded)
    }

    private suspend fun loadMainGameCollection(): GameCollectionModel {
        val gameCollection = getTrackedGamesInteractor.execute()
        this.gameCollection = gameCollection
        return gameCollection
    }

}