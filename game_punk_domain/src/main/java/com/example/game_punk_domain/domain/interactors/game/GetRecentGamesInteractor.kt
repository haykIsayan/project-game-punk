package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameSort

class GetRecentGamesInteractor constructor(
    private val getGameQueryWithRecentDatesInteractor: GetGameQueryWithRecentDatesInteractor,
    private val getGamesInteractor: GetGamesInteractor
) {
    suspend fun execute(): List<GameEntity> {
        val gamesQuery = getGameQueryWithRecentDatesInteractor.execute()
        return getGamesInteractor.execute(
            gamesQuery.copy(
                filter = GameFilter.highestRated,
                sort = GameSort.recent
            )
        )
    }
}