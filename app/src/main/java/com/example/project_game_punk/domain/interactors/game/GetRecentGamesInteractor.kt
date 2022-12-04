package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.PunkParamInteractor
import com.example.project_game_punk.data.game.rawg.models.GameModel
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort

class GetRecentGamesInteractor constructor(
    private val getGamesInteractor: PunkParamInteractor<List<GameModel>, GameQueryModel>
) {
    suspend fun execute(): List<GameEntity> {
        val gameQuery = GameQueryModel(sort = GameSort.trending)
        return getGamesInteractor.execute(gameQuery)
    }
}