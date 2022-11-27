package com.example.project_game_punk.features.search

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.domain.interactors.game.GetGamesInteractor
import com.example.project_game_punk.domain.interactors.game.GetTrendingGamesInteractor
import com.example.project_game_punk.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.common.update
import com.example.project_game_punk.features.discover.recent.GameSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val updateGameProgressInteractor: UpdateGameProgressInteractor,
    private val getTrendingGamesInteractor: GetTrendingGamesInteractor,
    private val getGamesInteractor: GetGamesInteractor,
): StateViewModel<List<GameEntity>, GameQueryModel>() {

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgress) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgress(gameProgress) as GameModel) },
            execute = { updateGameProgressInteractor.execute(game as GameModel, gameProgress) },
            onFail = { updateGames(game as GameModel) },
        )
    }

    private fun updateGames(game: GameModel) {
        val games = getData()
        val updatedGames = games?.toMutableList()?.apply { update(game) }?.toList()
        updatedGames?.apply { emit(GameSuccessState(updatedGames)) }
    }

    override suspend fun loadData(param: GameQueryModel?): List<GameEntity> {
        if (param == null) return emptyList()
        return getGamesInteractor.execute(param)
    }

    fun searchGames(query: GameQueryModel) {
        loadState(param = query)
    }

}