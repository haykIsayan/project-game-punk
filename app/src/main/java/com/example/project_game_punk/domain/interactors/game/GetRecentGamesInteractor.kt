package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.PunkParamInteractor
import com.example.project_game_punk.data.game.rawg.models.GameModel
import com.example.project_game_punk.domain.models.GameFilter
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort

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