package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.models.GameCollectionModel
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.update

class UpdateGameProgressInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
    private val addGameToGameCollectionInteractor: AddGameToGameCollectionInteractor,
    private val removeGameFromGameCollectionInteractor: RemoveGameFromGameCollectionInteractor,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(game: GameEntity, gameProgress: GameProgress): GameEntity? {
        val mainGameCollection = trackedGamesCache.getMainGameCollection()
        return when (gameProgress) {
            GameProgress.FollowingGameProgress -> {
                addToMainCollection(game, gameProgress, mainGameCollection)
            }
            GameProgress.NotFollowingGameProgress -> {
                removeFromMainCollection(game, gameProgress, mainGameCollection)
            }
            else -> {
                updateProgress(game, gameProgress, mainGameCollection)
            }
        }
    }

    private suspend fun addToMainCollection(
        game: GameEntity,
        gameProgress: GameProgress,
        mainGameCollection: GameCollectionModel
    ): GameEntity {
        val updatedGame = game.updateGameProgress(gameProgress) as GameModel
        val updatedMainGameCollection = addGameToGameCollectionInteractor.execute(
            updatedGame,
            mainGameCollection
        )
        trackedGamesCache.updateCache(updatedMainGameCollection)
        return updatedGame
    }

    private suspend fun removeFromMainCollection(
        game: GameEntity,
        gameProgress: GameProgress,
        mainGameCollection: GameCollectionModel,
    ): GameEntity {
        val updatedGame = game.updateGameProgress(gameProgress) as GameModel
        val updatedMainGameCollection = removeGameFromGameCollectionInteractor.execute(
            game,
            mainGameCollection
        )
        trackedGamesCache.updateCache(updatedMainGameCollection)
        return updatedGame
    }

    private suspend fun updateProgress(
        game: GameEntity,
        gameProgress: GameProgress,
        mainGameCollection: GameCollectionModel
    ): GameEntity? {
        val mainGames = mainGameCollection.games
        val gameToUpdate = mainGames.find { it.id == game.id } ?: return null
        val updatedGame = gameToUpdate.updateGameProgress(gameProgress) as? GameModel ?: return null
        val updatedGames = mainGames.toMutableList().apply { update(updatedGame) }
        val updatedCollection = mainGameCollection.copy(games = updatedGames)
        gameCollectionRepository.updateGameCollection(updatedCollection)
        trackedGamesCache.updateCache(updatedCollection)
        return updatedGame
    }
}