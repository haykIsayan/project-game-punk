package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.models.GameQueryModel

class GetUserGameQueryInteractor(
    private val getGameQueryWithRecentDatesInteractor: GetGameQueryWithRecentDatesInteractor
) {
    suspend fun execute(): GameQueryModel {
        val gameQueryModel = getGameQueryWithRecentDatesInteractor.execute()
        return GameQueryModel(
//            sort = GameSort.trending
        )
    }
}