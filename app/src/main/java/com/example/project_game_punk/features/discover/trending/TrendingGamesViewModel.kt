package com.example.project_game_punk.features.discover.trending

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.domain.interactors.game.GetTrendingGamesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingGamesViewModel @Inject constructor(
    private val trendingGamesInteractor: GetTrendingGamesInteractor
): StateViewModel<List<GameEntity>, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameEntity> {
        return trendingGamesInteractor.execute()
    }
}