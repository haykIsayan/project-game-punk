package com.example.project_game_punk.features.discover.recent

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.domain.interactors.game.GetRecommendedGamesInteractor
import com.example.project_game_punk.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.domain.interactors.game_collection.tracking.TrackUntrackGameInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.common.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GameSuccessState(data: List<GameModel>): ViewModelState.SuccessState<List<GameModel>>(data) {
    override fun equals(other: Any?): Boolean {
        if (other !is GameSuccessState) return false
        val otherData = other.data

        return data == otherData &&
                data.map {
                    it.isAdded
                } == otherData.map {
            it.isAdded
                } &&
                data.map {
                    it.gameProgress
                } == otherData.map {
            it.gameProgress
        }
    }
}

@HiltViewModel
class RecommendedGameViewModel @Inject constructor(
    private val getRecommendedGamesInteractor: GetRecommendedGamesInteractor,
    private val trackUntrackGameInteractor: TrackUntrackGameInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor
): StateViewModel<List<GameModel>, Unit>() {

    init {
        loadState()
    }


    fun updateGameProgress(game: GameEntity, gameProgress: GameProgress) {
        if (!game.isAdded) trackUntrackGame(game as GameModel)
        executeIO(Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgress(gameProgress) as GameModel) },
            execute = { updateGameProgressInteractor.execute(game.id!!, gameProgress) },
            onFail = { updateGames(game as GameModel) },
        )
    }

    private fun trackUntrackGame(game: GameModel) {
        executeIO(Dispatchers.IO,
            onBefore = { updateGames(game.copy(isAdded = !game.isAdded)) },
            execute = { trackUntrackGameInteractor.execute(game) },
            onFail = { updateGames(game) },
        )
    }


    private fun updateGames(game: GameModel) {
        val games = getData()
        val updatedGames = games?.toMutableList()?.apply { update(game) }?.toList()
        updatedGames?.apply { emit(GameSuccessState(updatedGames)) }
    }


    override suspend fun loadData(param: Unit?): List<GameModel> {
        return getRecommendedGamesInteractor.execute()
    }
}