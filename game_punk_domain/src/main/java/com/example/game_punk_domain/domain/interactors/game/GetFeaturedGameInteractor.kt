package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameSort

class GetFeaturedGameInteractor(
    private val gameRepository: GameRepository,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(): GameEntity {
        val gameQuery = GetGameQueryWithRecentDatesInteractor().execute()
        val game = gameRepository.getGames(
            gameQuery.copy(
                filter = GameFilter.highestRated,
                sort = GameSort.recent,
                gameMetaQuery = GameMetaQueryModel(
                    genres = true,
                    synopsis = true
                ),
            )
        ).apply {
            sortedBy {
                it.score
            }
        }.subList(0, 5).random()
        return trackedGamesCache.applyCache(game)
    }
}