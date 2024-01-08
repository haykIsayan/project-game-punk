package com.example.project_game_punk.features.common.game_punk_grid

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetGamesInteractor
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePunkGridViewModel @Inject constructor(
    private val getGamesInteractor: GetGamesInteractor
): StateViewModel<List<GameEntity>, List<String>>() {

    override suspend fun loadData(param: List<String>?): List<GameEntity> {
        param ?: return emptyList()
        val query = GameQueryModel(
            ids = param
        )
        return getGamesInteractor.execute(query)
    }
}