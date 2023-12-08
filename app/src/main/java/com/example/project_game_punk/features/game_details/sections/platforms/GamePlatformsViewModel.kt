package com.example.project_game_punk.features.game_details.sections.platforms

import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.interactors.game.GetGamePlatformsInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePlatformsViewModel @Inject constructor(
    private val getGamePlatformsInteractor: GetGamePlatformsInteractor
): StateViewModel<List<GamePlatformEntity>, String>(){
    override suspend fun loadData(param: String?): List<GamePlatformEntity> {
        if (param == null) return emptyList()
        return getGamePlatformsInteractor.execute(param)
    }
}