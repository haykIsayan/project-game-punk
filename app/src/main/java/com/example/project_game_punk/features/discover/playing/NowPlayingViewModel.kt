package com.example.project_game_punk.features.discover.playing

import com.example.project_game_punk.domain.interactors.game.GetNowPlayingGamesInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor
): StateViewModel<List<GameModel>, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameModel> {
        return getNowPlayingGamesInteractor.execute()
    }
}