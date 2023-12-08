package com.example.project_game_punk.features.game_details.sections.dlc

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetGamesDLCsInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDLCsViewModel @Inject constructor(
    private val getGamesDLCsInteractor: GetGamesDLCsInteractor
): StateViewModel<List<GameEntity>, String>(){
    override suspend fun loadData(param: String?): List<GameEntity> {
        if (param == null) return emptyList()
        return getGamesDLCsInteractor.execute(param)
    }
}


