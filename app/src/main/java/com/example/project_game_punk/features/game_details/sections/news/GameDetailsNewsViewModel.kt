package com.example.project_game_punk.features.game_details.sections.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsNewsViewModel @Inject constructor(
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
): StateViewModel<List<GameNewsEntity>, String>()  {
    override suspend fun loadData(param: String?): List<GameNewsEntity> {
        return param?.let { gameId ->
            getNewsForGameInteractor.execute(gameId)
        } ?: emptyList()
    }
}