package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.update

class UpdateGameExperiencePlatformInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(game: GameEntity, platformId: String): GameEntity {
        val mainCollection = trackedGamesCache.getMainGameCollection()
        val mainGames = mainCollection?.games
        val gameToUpdate = mainCollection?.games?.find { trackedGame -> trackedGame.id == game.id }
        val gameExperienceToUpdate = gameToUpdate?.gameExperience
        val updatedGameExperience = gameExperienceToUpdate?.updatePlatformId(platformId) ?: return game
        val updatedGame = gameToUpdate.updateGameExperience(updatedGameExperience)
        val updatedGames = mainGames?.toMutableList()?.apply { update(updatedGame) } ?: return game
        val updatedCollection = mainCollection.withGames(updatedGames)
        gameCollectionRepository.updateGameCollection(updatedCollection)
        trackedGamesCache.updateCache(updatedCollection)
        return updatedGame
    }
}