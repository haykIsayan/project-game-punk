package com.example.project_game_punk.features.game_details.sections.game_stores

import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameStoresInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameStoresViewModel @Inject constructor(
    private val getGameStoresInteractor: GetGameStoresInteractor
) : StateViewModel<List<GameStoreEntity>, String>(){
    override suspend fun loadData(param: String?): List<GameStoreEntity> {
        if (param == null) return emptyList()
        return getGameStoresInteractor.execute(param)
    }
}