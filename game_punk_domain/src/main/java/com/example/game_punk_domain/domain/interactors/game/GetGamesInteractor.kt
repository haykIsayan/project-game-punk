package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameQueryModel

class GetGamesInteractor constructor(
    private val trackedGamesCache: TrackedGamesCache,
    private val gameRepository: GameRepository,
) {
    suspend fun execute(gameQuery: GameQueryModel): List<GameEntity> {
        val games = gameRepository.getGames(gameQuery)
        return trackedGamesCache.applyCache(games)
    }
}