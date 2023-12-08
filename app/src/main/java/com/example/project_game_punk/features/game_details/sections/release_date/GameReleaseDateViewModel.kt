package com.example.project_game_punk.features.game_details.sections.release_date

import com.example.game_punk_domain.domain.interactors.game.GetGameReleaseDateInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameReleaseDateViewModel @Inject constructor(
    private val getGameReleaseDateInteractor: GetGameReleaseDateInteractor
): StateViewModel<String?, String>(){
    override suspend fun loadData(param: String?): String? {
        if (param == null) return null
        return getGameReleaseDateInteractor.execute(param)
    }
}