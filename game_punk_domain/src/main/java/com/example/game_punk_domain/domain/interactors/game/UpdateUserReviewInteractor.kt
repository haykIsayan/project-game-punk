package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.update

class UpdateUserReviewInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
    private val trackedGamesCache: TrackedGamesCache,
) {

    suspend fun execute(game: GameEntity, userReview: String): GameEntity {
        val mainCollection = trackedGamesCache.getMainGameCollection()
        val mainGames = mainCollection?.games
        val gameToUpdate = mainCollection?.games?.find { trackedGame -> trackedGame.id == game.id }
        gameToUpdate?.updateUserReview(userReview)?.let { updatedGame ->
            mainGames?.toMutableList()?.apply { update(updatedGame) }?.let { updatedGames ->
                val updatedCollection = mainCollection.withGames(updatedGames)
                gameCollectionRepository.updateGameCollection(updatedCollection)
                trackedGamesCache.updateCache(updatedCollection)
            }
            return updatedGame
        }
        return game
    }
}