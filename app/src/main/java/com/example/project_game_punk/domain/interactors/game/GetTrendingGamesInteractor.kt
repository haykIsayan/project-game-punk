package com.example.project_game_punk.domain.interactors.game

import android.util.Log
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.models.GameSort

class GetTrendingGamesInteractor constructor(
    private val getGamesInteractor: GetGamesInteractor
) {
    suspend fun execute(): List<GameEntity> {
        val gameQuery = GetGameQueryWithRecentDatesInteractor().execute()

        val games = getGamesInteractor.execute(gameQuery.copy(sort = GameSort.trending, isHighestRated = false))


        return games.filter {
            it.numAdded >= 1000
        }
    }
}