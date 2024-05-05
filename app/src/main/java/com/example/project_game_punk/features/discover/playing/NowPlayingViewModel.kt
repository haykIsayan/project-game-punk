package com.example.project_game_punk.features.discover.playing

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val getGamesInteractor: GetGamesInteractor,
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor,
    private val getGameArtworksInteractor: GetGameArtworksInteractor
): StateViewModel<NowPlayingState, String?>() {
//
//    init {
//        loadState()
//    }

    override suspend fun loadData(param: String?): NowPlayingState {
        val nowPlayingGames = getNowPlayingGamesInteractor.execute(param).reversed()
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
        val nowPlayingGameStates = nowPlayingGames.map { game ->
            viewModelScope.async {
                val gameId = game.id ?: return@async null
                val artwork = getGameArtworksInteractor.execute(gameId).first()
                NowPlayingGameState(
                    game = game,
                    artwork = artwork
                )
            }
        }.awaitAll().filterNotNull()

        return NowPlayingState.NowPlayingAvailable(
            nowPlayingGameStates
        )
    }
}

sealed class NowPlayingState {
    data class NowPlayingAvailable(
        val nowPlayingGames: List<NowPlayingGameState>,
    ): NowPlayingState()

    data class NowPlayingUnavailable(
        val games: List<GameEntity>
    ): NowPlayingState()
}

data class NowPlayingGameState(
    val game: GameEntity,
    val artwork: String,
)