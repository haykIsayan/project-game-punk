package com.example.project_game_punk.features.game_details.sections.similar_games

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetSimilarGamesInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsSimilarGamesViewModel @Inject constructor(
    private val getSimilarGamesInteractor: GetSimilarGamesInteractor
): StateViewModel<List<GameEntity>, String>(){
    override suspend fun loadData(param: String?): List<GameEntity> {
        if (param == null) return emptyList()
        return getSimilarGamesInteractor.execute(param)
    }
}