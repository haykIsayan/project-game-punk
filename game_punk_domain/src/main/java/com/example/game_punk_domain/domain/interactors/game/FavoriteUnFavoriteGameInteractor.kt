package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.update

class FavoriteUnFavoriteGameInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(game: GameEntity): GameEntity {
        val mainCollection = trackedGamesCache.getMainGameCollection()
        val mainGames = mainCollection?.games
        val gameToFavorite = mainCollection?.games?.find { trackedGame -> trackedGame.id == game.id }
        gameToFavorite?.toggleIsFavorite()?.let { updatedGame ->
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