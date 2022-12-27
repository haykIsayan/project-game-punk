package com.example.project_game_punk.features.discover.recent

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.interactors.game.GetRecentGamesInteractor
import com.example.project_game_punk.domain.interactors.game.GetRecommendedGamesInteractor
import com.example.project_game_punk.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.common.update
import com.example.project_game_punk.features.discover.recommended.GameSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RecentGamesViewModel @Inject constructor(
    private val getRecentGamesInteractor: GetRecentGamesInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor
): StateViewModel<List<GameEntity>, Unit>() {

    init {
        loadState()
    }

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgress) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgress(gameProgress)) },
            execute = { updateGameProgressInteractor.execute(game, gameProgress) },
            onFail = { updateGames(game) },
        )
    }

    private fun updateGames(game: GameEntity) {
        val games = getData()
        val updatedGames = games?.toMutableList()?.apply { update(game) }?.toList()
        updatedGames?.apply { emit(GameSuccessState(updatedGames)) }
    }


    override suspend fun loadData(param: Unit?): List<GameEntity> {
        return getRecentGamesInteractor.execute()
    }
}