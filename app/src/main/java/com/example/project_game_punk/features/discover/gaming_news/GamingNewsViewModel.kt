package com.example.project_game_punk.features.discover.gaming_news

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetRecentGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.GetTrendingGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

@HiltViewModel
class GamingNewsViewModel @Inject constructor(
    private val getGameArtworksInteractor: GetGameArtworksInteractor,
    private val getTrendingGamesInteractor: GetTrendingGamesInteractor,
    private val getRecentGamesInteractor: GetRecentGamesInteractor,
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
): StateViewModel<List<GamingNewsEntityState>, String>() {

    init {
        loadState()
    }



    override suspend fun loadData(param: String?): List<GamingNewsEntityState> {
        val gamesToGetNewsFor = awaitAll(
            viewModelScope.async {
                getTrendingGamesInteractor.execute().shuffled()
            },
            viewModelScope.async {
                getRecentGamesInteractor.execute().shuffled()
            }


        ).flatten()/*.subList(0, 5)*/

        val gamingNews = gamesToGetNewsFor.map { game ->
            viewModelScope.async {
                game.id?.let { gameId ->
                    getNewsForGameInteractor.execute(gameId = gameId)
                }?.firstOrNull()?.let { news ->
                    GamingNewsEntityState(
                        artwork = null,
                        game = game,
                        gameNews = news
                    )
                }
            }
        }.awaitAll().filter {
            it?.gameNews?.author != "Community Announcements"
        }

        val states = gamingNews.filterNotNull()

        emitAsync(ViewModelState.SuccessState(states))



        val artworks = gamesToGetNewsFor.map { game ->
            viewModelScope.async {
                game.id?.let { gameId ->
                    getGameArtworksInteractor.execute(id = gameId)
                }?.firstOrNull()?.let { artwork ->
                    GamingNewsEntityState(
                        artwork = artwork,
                        game = game,
                        gameNews = null
                    )
                }
            }
        }.awaitAll().filterNotNull()



        val updatedStates = states.map { newsState ->
            val artwork = artworks.find {it.game.id == newsState.game.id }
            newsState.copy(artwork = artwork?.artwork)
        }

        Log.d("Haykk", "$updatedStates")

        return updatedStates
    }
}

data class GamingNewsEntityState(
    val artwork: String?,
    val game: GameEntity,
    val gameNews: GameNewsEntity?
)