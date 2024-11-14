package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.game.GameEntity

class GetFeaturedGameInteractor(
    private val getRecentGamesInteractor: GetRecentGamesInteractor,
    private val getGameInteractor: GetGameInteractor
    ) {
    suspend fun execute(): GameEntity {
        return getRecentGamesInteractor.execute().subList(0, 5).random()
    }
}