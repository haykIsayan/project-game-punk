package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
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
    suspend fun execute(game: GameEntity, gameProgressStatus: GameProgressStatus): GameEntity? {
        val mainGameCollection = trackedGamesCache.getMainGameCollection()
        return mainGameCollection?.let {
            when (gameProgressStatus) {
                GameProgressStatus.following -> {
                    addToMainCollection(game, gameProgressStatus, mainGameCollection)
                }
                GameProgressStatus.notFollowing -> {
                    removeFromMainCollection(game, gameProgressStatus, mainGameCollection)
                }
                else -> {
                    updateProgress(game, gameProgressStatus, mainGameCollection)
                }
            }
        }
    }

    private suspend fun addToMainCollection(
        game: GameEntity,
        gameProgressStatus: GameProgressStatus,
        mainGameCollection: GameCollectionEntity
    ): GameEntity {
        val updatedGame = game.updateGameProgressStatus(gameProgressStatus)
        val updatedMainGameCollection = addGameToGameCollectionInteractor.execute(
            updatedGame,
            mainGameCollection
        )
        trackedGamesCache.updateCache(updatedMainGameCollection)
        return updatedGame
    }

    private suspend fun removeFromMainCollection(
        game: GameEntity,
        gameProgressStatus: GameProgressStatus,
        mainGameCollection: GameCollectionEntity,
    ): GameEntity {
        val updatedGame = game.updateGameProgressStatus(gameProgressStatus)
        val updatedMainGameCollection = removeGameFromGameCollectionInteractor.execute(
            game,
            mainGameCollection
        )
        trackedGamesCache.updateCache(updatedMainGameCollection)
        return updatedGame
    }

    private suspend fun updateProgress(
        game: GameEntity,
        gameProgressStatus: GameProgressStatus,
        mainGameCollection: GameCollectionEntity
    ): GameEntity? {
        val mainGames = mainGameCollection.games
        val gameToUpdate = mainGames.find { it.id == game.id } ?: return null
        val updatedGame = gameToUpdate.updateGameProgressStatus(gameProgressStatus)
        val updatedGames = mainGames.toMutableList().apply { update(updatedGame) }
        val updatedCollection = mainGameCollection.withGames(updatedGames)
        gameCollectionRepository.updateGameCollection(updatedCollection)
        trackedGamesCache.updateCache(updatedCollection)
        return updatedGame
    }
}