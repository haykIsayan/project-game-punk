package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameEntity

class GetRecommendedGamesInteractor(
    private val gameUserGameQueryInteractor: GetUserGameQueryInteractor,
    private val getGamesInteractor: GetGamesInteractor
) {
    suspend fun execute(): List<GameEntity> {
        val gameQuery = gameUserGameQueryInteractor.execute().copy(query = "Assassin's Creed")
        return getGamesInteractor.execute(gameQuery)
    }
}