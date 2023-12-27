package com.example.project_game_punk.features.discover.featured

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.features.common.StateViewModel
import com.example.game_punk_domain.domain.interactors.game.GetFeaturedGameInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameScreenshotsInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.common.update
import com.example.project_game_punk.features.discover.recommended.GameSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FeaturedGameViewModel @Inject constructor(
    private val getFeaturedGameInteractor: GetFeaturedGameInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor,
    private val getGameScreenshotsInteractor: GetGameScreenshotsInteractor
): StateViewModel<FeaturedGameUiModel, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): FeaturedGameUiModel {
        val game = getFeaturedGameInteractor.execute()
        val screenshots = game.id?.let { gameId ->
            getGameScreenshotsInteractor.execute(gameId)
        } ?: emptyList()
        return FeaturedGameUiModel(game, screenshots)
    }

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgressStatus) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGame(game.updateGameProgressStatus(gameProgress)) },
            execute = { updateGameProgressInteractor.execute(game, gameProgress) },
            onFail = { updateGame(game) },
        )
    }

    private fun updateGame(game: GameEntity) {
        val uiModel = getData() ?: return
        emit(ViewModelState.SuccessState(uiModel.copy(game = game)))
    }

}

data class FeaturedGameUiModel(
    val game: GameEntity,
    val screenshots: List<String>
)