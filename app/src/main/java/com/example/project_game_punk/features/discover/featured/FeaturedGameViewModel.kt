package com.example.project_game_punk.features.discover.featured

import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.features.common.StateViewModel
import com.example.game_punk_domain.domain.interactors.game.GetFeaturedGameInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameArtworksInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameScreenshotsInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameProgressInteractor
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FeaturedGameViewModel @Inject constructor(
    private val getFeaturedGameInteractor: GetFeaturedGameInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor,
    private val getGameScreenshotsInteractor: GetGameScreenshotsInteractor,
    private val getGameArtworksInteractor: GetGameArtworksInteractor,
): StateViewModel<FeaturedGameUiModel, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): FeaturedGameUiModel {
        val game = getFeaturedGameInteractor.execute()

        emitAsync(
            ViewModelState.SuccessState(
                FeaturedGameUiModel(
                    game,
                    listOf(
                        "",
                        "",
                        "",
                        ""
                    ),
                    null
                )
            )
        )

        val artwork = game.id?.let { gameId ->
            getGameArtworksInteractor.execute(gameId).first()
        }

        emitAsync(
            ViewModelState.SuccessState(
                FeaturedGameUiModel(
                    game,
                    emptyList(),
                    null
                )
            )
        )

//        val stores = game.id?.let { gameId ->
//            getGameStoresInteractor.execute(gameId)
//        }


        val screenshots = game.id?.let { gameId ->
            getGameScreenshotsInteractor.execute(gameId)
        } ?: emptyList()

        return FeaturedGameUiModel(
            game,
            screenshots,
            artwork,
        )
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
    val screenshots: List<String>,
    val artwork: String?
)