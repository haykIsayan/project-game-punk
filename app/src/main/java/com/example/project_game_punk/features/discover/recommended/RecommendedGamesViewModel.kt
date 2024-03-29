package com.example.project_game_punk.features.discover.recommended

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.features.common.StateViewModel
import com.example.game_punk_domain.domain.interactors.game.GetRecommendedGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.common.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GameSuccessState(data: List<GameEntity>): ViewModelState.SuccessState<List<GameEntity>>(data) {
    override fun equals(other: Any?): Boolean {
        if (other !is GameSuccessState) return false
        val otherData = other.data

        return data == otherData &&
                data.map {
                    it.gameExperience?.gameProgressStatus
                } == otherData.map {
            it.gameExperience?.gameProgressStatus
        }
    }
}

@HiltViewModel
class RecommendedGamesViewModel @Inject constructor(
    private val getRecommendedGamesInteractor: GetRecommendedGamesInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor
): StateViewModel<List<GameEntity>, Unit>() {

    init {
        loadState()
    }

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgressStatus) {
        executeIO(Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgressStatus(gameProgress)) },
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
        return getRecommendedGamesInteractor.execute()
    }
}