package com.example.project_game_punk.features.game_details.sections.screenshots

import com.example.project_game_punk.domain.interactors.game.GetGameScreenshotsInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenshotsViewModel @Inject constructor(
    private val getGameScreenshotsInteractor: GetGameScreenshotsInteractor
): StateViewModel<List<String>, String>() {

    override suspend fun loadData(param: String?): List<String> {
        if (param == null) return emptyList()
        return getGameScreenshotsInteractor.execute(param)
    }

}