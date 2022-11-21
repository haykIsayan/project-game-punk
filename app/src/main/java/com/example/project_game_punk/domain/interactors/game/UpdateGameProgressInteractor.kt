package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.update

class UpdateGameProgressInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(gameId: String, gameProgress: GameProgress): GameEntity? {
        val mainGameCollection = trackedGamesCache.getMainGameCollection()
        val mainGames = mainGameCollection.games
        val gameToUpdate = mainGames.find { it.id == gameId } ?: return null
        val updatedGame = gameToUpdate.updateGameProgress(gameProgress) as? GameModel ?: return null
        val updatedGames = mainGames.toMutableList().apply { update(updatedGame) }
        val updatedCollection = mainGameCollection.copy(games = updatedGames)
        gameCollectionRepository.updateGameCollection(updatedCollection)
        trackedGamesCache.updateCache(updatedCollection)
        return updatedGame
    }
}