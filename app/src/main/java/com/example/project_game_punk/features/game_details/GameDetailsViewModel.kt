package com.example.project_game_punk.features.game_details

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameInteractor: GetGameInteractor,
): StateViewModel<GameEntity?, String>() {

    fun loadGame(id: String) {
        loadState(param = id)
    }

    override suspend fun loadData(param: String?): GameEntity? {
        if (param == null) return null
        return getGameInteractor.execute(param)
    }
}