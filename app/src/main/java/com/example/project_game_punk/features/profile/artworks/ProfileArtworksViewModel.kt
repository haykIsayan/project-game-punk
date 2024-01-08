package com.example.project_game_punk.features.profile.artworks

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

@HiltViewModel
class ProfileArtworksViewModel @Inject constructor(
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor,
    private val getGameArtworksInteractor: GetGameArtworksInteractor
): StateViewModel<List<String>, String>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: String?): List<String> {
        val nowPlayingGames = getNowPlayingGamesInteractor.execute()
        // todo reduce now playing games to 5
        val nowPlayingArtworks = nowPlayingGames.map { game ->
            viewModelScope.async {
                game.id?.let { gameId ->
                    getGameArtworksInteractor.execute(id = gameId)
                }?.randomOrNull()
            }
        }.toList().awaitAll().filterNotNull()
        return nowPlayingArtworks
    }
}