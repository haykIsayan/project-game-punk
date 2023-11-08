package com.example.project_game_punk.features.game_details.sections.genre

import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameGenresInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameGenresViewModel @Inject constructor(
    private val getGameGenresInteractor: GetGameGenresInteractor
) : StateViewModel<List<GameGenreEntity>, String>(){
    override suspend fun loadData(param: String?): List<GameGenreEntity> {
        if (param == null) return emptyList()
        return getGameGenresInteractor.execute(param)
    }
}