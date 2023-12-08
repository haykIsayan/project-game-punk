package com.example.project_game_punk.features.discover.playing

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val getGamesInteractor: GetGamesInteractor,
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor
): StateViewModel<NowPlayingState, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): NowPlayingState {
        val nowPlayingGames = getNowPlayingGamesInteractor.execute().reversed()
        if (nowPlayingGames.isEmpty()) {
            return NowPlayingState.NowPlayingUnavailable(
                getGamesInteractor.execute(
                    GameQueryModel(
                        filter = GameFilter.trending,
                        sort = GameSort.trending
                    )
                )
                    .shuffled()
                    .subList(0, 4)
            )
        }
        return NowPlayingState.NowPlayingAvailable(
            nowPlayingGames
        )
    }
}

sealed class NowPlayingState {
    data class NowPlayingAvailable(
        val nowPlayingGames: List<GameEntity>,
    ): NowPlayingState()

    data class NowPlayingUnavailable(
        val games: List<GameEntity>
    ): NowPlayingState()
}