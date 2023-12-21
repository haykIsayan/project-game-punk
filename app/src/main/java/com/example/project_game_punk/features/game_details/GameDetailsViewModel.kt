package com.example.project_game_punk.features.game_details

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interactors.game.FavoriteUnFavoriteGameInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameInteractor: GetGameInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor,
    private val favoriteUnFavoriteGameInteractor: FavoriteUnFavoriteGameInteractor
): StateViewModel<GameEntity?, String>() {

    fun loadGame(id: String) {
        loadState(param = id)
    }

    override suspend fun loadData(param: String?): GameEntity? {
        if (param == null) return null
        return getGameInteractor.execute(param)
    }

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgressStatus) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgressStatus(gameProgress)) },
            execute = { updateGameProgressInteractor.execute(game, gameProgress) },
            onFail = { updateGames(game) },
        )
    }

    fun updateIsFavorite(game: GameEntity) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.toggleIsFavorite()) },
            execute = { favoriteUnFavoriteGameInteractor.execute(game) },
            onFail = { updateGames(game) },
        )
    }

    private fun updateGames(game: GameEntity) {
        emit(ViewModelState.SuccessState(game))
    }
}