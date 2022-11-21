package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.models.GameModel

class GetRecommendedGamesInteractor(
    private val gameUserGameQueryInteractor: GetUserGameQueryInteractor,
    private val getGamesInteractor: GetGamesInteractor
) {
    suspend fun execute(): List<GameModel> {
        val gameQuery = gameUserGameQueryInteractor.execute()
        return getGamesInteractor.execute(gameQuery)
    }
}