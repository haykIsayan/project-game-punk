package com.example.project_game_punk.features.discover.news

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameScreenshotsInteractor
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameNewsViewModel @Inject constructor(
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor,
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
    private val getGameScreenshotsInteractor: GetGameScreenshotsInteractor
): StateViewModel<GameNewsEntityState, String>()  {

    init {
        loadState("100")
    }

    override suspend fun loadData(param: String?): GameNewsEntityState {
        val lastPlayedGame = getNowPlayingGamesInteractor.execute().random()
        val lastPlayedGameId = lastPlayedGame.id ?: return GameNewsEntityState(
            lastPlayedGame,
            emptyList()
        )
        val lastPayedGameNews = getNewsForGameInteractor.execute(lastPlayedGameId)
        return GameNewsEntityState(
            lastPlayedGame,
            lastPayedGameNews
        )
    }
}

data class GameNewsEntityState(
    val game: GameEntity,
    val gameNews: List<GameNewsEntity>
)