package com.example.project_game_punk.domain

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor

class TrackedGamesCache(
    private val getTrackedGamesInteractor: GetTrackedGamesInteractor,
) {

    private var gameCollection: GameCollectionEntity? = null

    fun updateCache(updatedMainGameCollection: GameCollectionEntity) {
        this.gameCollection = updatedMainGameCollection
    }

    suspend fun getMainGameCollection(): GameCollectionEntity {
        return gameCollection ?: loadMainGameCollection()
    }

    suspend fun applyCache(games: List<GameEntity>): List<GameEntity> {
        val mainGameCollection = getMainGameCollection()
        val updatedGames = games.map { game ->
            val cachedGame = mainGameCollection.games.find { it.id == game.id }
            if (cachedGame != null) game.updateGameProgress(cachedGame.gameProgress) else game
        }
        return updatedGames
    }

    suspend fun applyCache(game: GameEntity): GameEntity {
        val mainGameCollection = getMainGameCollection()
        val trackedGames = mainGameCollection.games
        val cachedGame = trackedGames.find { it.id == game.id }
        return game.updateGameProgress(
            cachedGame?.gameProgress ?: GameProgress.NotFollowingGameProgress
        )
    }

    private suspend fun loadMainGameCollection(): GameCollectionEntity {
        val gameCollection = getTrackedGamesInteractor.execute()
        this.gameCollection = gameCollection
        return gameCollection
    }

}