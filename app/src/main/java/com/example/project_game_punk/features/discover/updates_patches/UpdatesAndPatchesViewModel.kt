package com.example.project_game_punk.features.discover.updates_patches

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetUserTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.dateToMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

@HiltViewModel
class UpdatesAndPatchesViewModel @Inject constructor(
    private val getUserTrackedGamesInteractor: GetUserTrackedGamesInteractor,
    private val getGameArtworksInteractor: GetGameArtworksInteractor,
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
): StateViewModel<List<GameNewsEntityState>, Unit>()  {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameNewsEntityState> {
        val gamesToGetNewsFor = getUserTrackedGamesInteractor.execute(null)
            .filter {
                it.gameExperience?.gameProgressStatus == GameProgressStatus.excited
                        || it.gameExperience?.gameProgressStatus == GameProgressStatus.playing
                        || it.gameExperience?.gameProgressStatus == GameProgressStatus.replaying
            }
            .let {
                if (it.size > 10) it.subList(0, 10) else it
            }
        val topNowPlayingNews = gamesToGetNewsFor.map { game ->
            viewModelScope.async {
                game.id?.let { gameId ->
                    getNewsForGameInteractor.execute(gameId = gameId)
                }?.firstOrNull()?.let { news ->
                    GameNewsEntityState(game, news)
                }
            }
        }.toList().awaitAll().filterNotNull().sortedByDescending {
            it.gameNews.date.dateToMillis()
        }.filter {
            it.gameNews.author == "Community Announcements"
        }
        return topNowPlayingNews
    }
}

data class GameNewsEntityState(
    val game: GameEntity,
    val gameNews: GameNewsEntity
)