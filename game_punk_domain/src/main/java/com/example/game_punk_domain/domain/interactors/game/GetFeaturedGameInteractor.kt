package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameSort

class GetFeaturedGameInteractor constructor(
    private val gameRepository: GameRepository,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(): GameEntity {
        val gameQuery = GetGameQueryWithRecentDatesInteractor().execute()
        val game = gameRepository.getGames(gameQuery.copy(sort = GameSort.trending)).apply {
            sortedBy {
                it.metaCriticScore
            }
        }.random()
        return trackedGamesCache.applyCache(game)
    }
}