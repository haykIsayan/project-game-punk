package com.example.project_game_punk.features.discover.news

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

@HiltViewModel
class GameNewsViewModel @Inject constructor(
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor,
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
): StateViewModel<List<GameNewsEntityState>, Unit>()  {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameNewsEntityState> {
        val nowPlayingGames = getNowPlayingGamesInteractor.execute()
        // todo reduce now playing games to 5
        val topNowPlayingNews = nowPlayingGames.map { game ->
            viewModelScope.async {
                game.id?.let { gameId ->
                    getNewsForGameInteractor.execute(gameId = gameId)
                }?.firstOrNull()?.let { news ->
                    GameNewsEntityState(game, news)
                }
            }
        }.toList().awaitAll().filterNotNull()
        return topNowPlayingNews
    }
}

data class GameNewsEntityState(
    val game: GameEntity,
    val gameNews: GameNewsEntity
)