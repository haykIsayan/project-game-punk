package com.example.project_game_punk.features.authentication

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetTrendingGamesInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class AuthTrendingGamesViewModel: StateViewModel<List<GameEntity>, Unit>() {

    init {
//        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameEntity> {
        return emptyList()
    }

}

//private val trendingGamesInteractor: GetTrendingGamesInteractor,