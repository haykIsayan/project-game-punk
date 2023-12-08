package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.models.GameQueryModel

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