package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort

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