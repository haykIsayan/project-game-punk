package com.example.project_game_punk.features.authentication

import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.project_game_punk.features.common.StateViewModel

class AuthTrendingGamesViewModel: StateViewModel<List<GameEntity>, Unit>() {

    init {
//        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameEntity> {
        return emptyList()
    }

}

//private val trendingGamesInteractor: GetTrendingGamesInteractor,