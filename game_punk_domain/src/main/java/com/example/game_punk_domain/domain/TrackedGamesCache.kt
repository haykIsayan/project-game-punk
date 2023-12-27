package com.example.game_punk_domain.domain

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor

class TrackedGamesCache(
    private val getTrackedGamesInteractor: GetTrackedGamesInteractor,
) {

    private var gameCollection: GameCollectionEntity? = null

    fun updateCache(updatedMainGameCollection: GameCollectionEntity) {
        this.gameCollection = updatedMainGameCollection
    }

    suspend fun getMainGameCollection(): GameCollectionEntity? {
        return gameCollection ?: loadMainGameCollection()
    }

    suspend fun applyCache(games: List<GameEntity>): List<GameEntity> {
        val mainGameCollection = getMainGameCollection()
        val updatedGames = games.map { game ->
            val cachedGame = mainGameCollection?.games?.find { it.id == game.id }
            val cachedGameExperience = cachedGame?.gameExperience
            if (cachedGame != null && cachedGameExperience!= null) game.updateGameExperience(cachedGameExperience) else game
        }
        return updatedGames
    }

    suspend fun applyCache(game: GameEntity): GameEntity {
        val mainGameCollection = getMainGameCollection()
        val trackedGames = mainGameCollection?.games
        val cachedGame = trackedGames?.find { it.id == game.id }
        return game.updateGameProgressStatus(
            cachedGame?.gameExperience?.gameProgressStatus ?: GameProgressStatus.notFollowing
        )
    }

    private suspend fun loadMainGameCollection(): GameCollectionEntity? {
        val gameCollection = getTrackedGamesInteractor.execute()
        this.gameCollection = gameCollection
        return gameCollection
    }

    fun clearCache() {
        gameCollection = null
    }

}