package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.interfaces.PunkInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameSort

class GetFeaturedGameInteractor constructor(
    private val gameRepository: GameRepository,
    private val trackedGamesCache: TrackedGamesCache
): PunkInteractor<GameModel> {
    override suspend fun execute(): GameModel {
        val gameQuery = GetGameQueryWithRecentDatesInteractor().execute()
        val game = gameRepository.getGames(gameQuery.copy(sort = GameSort.trending)).apply {
            sortedBy {
                it.metaCriticScore
            }
        }.random()
        return trackedGamesCache.applyCache(game)
    }
}