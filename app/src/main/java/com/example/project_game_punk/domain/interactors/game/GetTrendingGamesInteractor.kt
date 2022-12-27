package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.models.GameFilter
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort

class GetTrendingGamesInteractor constructor(
    private val getGamesInteractor: GetGamesInteractor
) {
    suspend fun execute(): List<GameEntity> {
        return getGamesInteractor.execute(
            GameQueryModel(
                filter = GameFilter.trending,
                sort = GameSort.recent
            )
        )
    }
}