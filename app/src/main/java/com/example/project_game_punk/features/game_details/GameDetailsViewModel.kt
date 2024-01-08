package com.example.project_game_punk.features.game_details

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interactors.game.FavoriteUnFavoriteGameInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameExperiencePlatformInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameExperienceStoreInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameProgressInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateUserReviewInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateUserScoreInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameInteractor: GetGameInteractor,
    private val updateGameExperiencePlatformInteractor: UpdateGameExperiencePlatformInteractor,
    private val updateGameExperienceStoreInteractor: UpdateGameExperienceStoreInteractor,
    private val updateUserScoreInteractor: UpdateUserScoreInteractor,
    private val updateUserReviewInteractor: UpdateUserReviewInteractor,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor,
    private val favoriteUnFavoriteGameInteractor: FavoriteUnFavoriteGameInteractor
): StateViewModel<GameEntity?, String>() {

    fun loadGame(id: String) {
        loadState(param = id)
    }

    override suspend fun loadData(param: String?): GameEntity? {
        if (param == null) return null
        val metaQuery = GameMetaQueryModel(
            platforms = true,
            genres = true
        )
        return getGameInteractor.execute(param, metaQuery)
    }

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgressStatus) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgressStatus(gameProgress)) },
            execute = { updateGameProgressInteractor.execute(game, gameProgress) },
            onFail = { updateGames(game) },
        )
    }

    fun updateGameExperiencePlatform(game: GameEntity, platformId: String) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameExperiencePlatform(platformId)) },
            execute = { updateGameExperiencePlatformInteractor.execute(game, platformId) },
            onFail = { updateGames(game) },
        )
    }

    fun updateGameExperienceStore(game: GameEntity, storeId: String) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameExperienceStore(storeId)) },
            execute = { updateGameExperienceStoreInteractor.execute(game, storeId) },
            onFail = { updateGames(game) },
        )
    }

    fun updateUserScore(game: GameEntity, userScore: Float) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateUserScore(userScore)) },
            execute = { updateUserScoreInteractor.execute(game, userScore) },
            onFail = { updateGames(game) },
        )
    }

    fun updateUserReview(game: GameEntity, userReview: String) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateUserReview(userReview)) },
            execute = { updateUserReviewInteractor.execute(game, userReview) },
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