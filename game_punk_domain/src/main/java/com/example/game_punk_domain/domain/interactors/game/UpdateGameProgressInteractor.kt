package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgress
import com.example.game_punk_domain.update
import com.example.game_punk_domain.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository

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
        mainGameCollection: GameCollectionEntity
    ): GameEntity {
        val updatedGame = game.updateGameProgress(gameProgress)
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
        mainGameCollection: GameCollectionEntity,
    ): GameEntity {
        val updatedGame = game.updateGameProgress(gameProgress)
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
        mainGameCollection: GameCollectionEntity
    ): GameEntity? {
        val mainGames = mainGameCollection.games
        val gameToUpdate = mainGames.find { it.id == game.id } ?: return null
        val updatedGame = gameToUpdate.updateGameProgress(gameProgress)
        val updatedGames = mainGames.toMutableList().apply { update(updatedGame) }
        val updatedCollection = mainGameCollection.withGames(updatedGames)
        gameCollectionRepository.updateGameCollection(updatedCollection)
        trackedGamesCache.updateCache(updatedCollection)
        return updatedGame
    }
}