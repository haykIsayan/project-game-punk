package com.example.project_game_punk.features.discover.countdown

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.interactors.game.GetExcitedAndInterestedGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameReleaseDateInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.dateToMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject


data class GameReleaseDateState(
    val background: String,
    val releaseDate: String,
    val game: GameEntity
)

@HiltViewModel
class CountdownViewModel @Inject constructor(
    private val getExcitedAndInterestedGamesInteractor: GetExcitedAndInterestedGamesInteractor,
    private val getGameReleaseDateInteractor: GetGameReleaseDateInteractor,
    private val getGameArtworksInteractor: GetGameArtworksInteractor,
    private val getGameInteractor: GetGameInteractor
): StateViewModel<List<GameReleaseDateState>, Unit>()  {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): List<GameReleaseDateState> {
        val exitedGames = getExcitedAndInterestedGamesInteractor.execute()
        val gameReleaseDateStates =
            exitedGames.map { game ->
                viewModelScope.async {

                    val gameId = game.id ?: return@async null

//                    val artwork = getGameArtworksInteractor.execute(gameId).firstOrNull() ?: return@async null
                    val artwork = getGameInteractor.execute(
                        id = gameId,
                        GameMetaQueryModel(
                            banner = true
                        )
                    ).banner ?: return@async null
                    val releaseDate = getGameReleaseDateInteractor.execute(gameId)
                    GameReleaseDateState(
                        artwork,
                        releaseDate,
                        game
                    )
                }
            }.awaitAll().sortedBy {
                it?.releaseDate?.dateToMillis()
            }
        return gameReleaseDateStates.filterNotNull()
    }

}