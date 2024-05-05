package com.example.project_game_punk.features.discover.gaming_news

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetRecentGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.GetTrendingGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.dateToMillis
import com.example.project_game_punk.features.discover.updates_patches.GameNewsEntityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
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
        val trendingGames = getTrendingGamesInteractor.execute().shuffled()
         val recentGames = getRecentGamesInteractor.execute().shuffled()
        val gamesToGetNewsFor = mutableListOf<GameEntity>().apply {
            addAll(trendingGames)
            addAll(recentGames)
        }

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
        }

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
        }
        val gamingNewsState = mutableListOf<Deferred<GamingNewsEntityState?>>().apply {
            addAll(artworks)
            addAll(gamingNews)
        }.awaitAll().asSequence().filterNotNull().groupBy {
            it.game.id
        }.map {
            if (it.value.size >= 2) {
                it.value[1].copy(artwork = it.value.first().artwork)
            } else {
                null
            }
        }.toList().filterNotNull().sortedByDescending {
            it.gameNews?.date?.dateToMillis()
        }.toList().filter { it.gameNews?.author != "Community Announcements"

        }
        return gamingNewsState
    }
}

data class GamingNewsEntityState(
    val artwork: String?,
    val game: GameEntity,
    val gameNews: GameNewsEntity?
)