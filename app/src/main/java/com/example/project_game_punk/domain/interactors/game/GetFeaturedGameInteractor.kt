package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.models.GameSort

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