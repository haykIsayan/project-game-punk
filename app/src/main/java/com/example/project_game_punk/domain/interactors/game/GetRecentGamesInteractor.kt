package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.interfaces.PunkInteractor
import com.example.project_game_punk.domain.interfaces.PunkParamInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort

class GetRecentGamesInteractor constructor(
    private val getGamesInteractor: PunkParamInteractor<List<GameModel>, GameQueryModel>
): PunkInteractor<List<GameModel>> {
    override suspend fun execute(): List<GameModel> {
        val gameQuery = GameQueryModel(sort = GameSort.trending)
        return getGamesInteractor.execute(gameQuery)
    }
}