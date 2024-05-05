package com.example.project_game_punk.features.game_details.sections.experience

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interactors.reviews.GameReviewFactory
import com.example.game_punk_domain.domain.interactors.reviews.GetUserReviewForGameInteractor
import com.example.game_punk_domain.domain.interactors.reviews.UpdateUserReviewForGameInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class GameUserReviewViewModel @Inject constructor(
    private val userCache: UserCache,
    private val gameReviewFactory: GameReviewFactory,
    private val getUserReviewForGameInteractor: GetUserReviewForGameInteractor,
    private val updateUserReviewForGameInteractor: UpdateUserReviewForGameInteractor
): StateViewModel<GameReviewEntity?, String>() {
    override suspend fun loadData(param: String?): GameReviewEntity? {
        val userId = userCache.userId() ?: return null
        val gameId = param ?: return null
        return getUserReviewForGameInteractor.execute(
            userId,
            gameId
        ) ?: gameReviewFactory.create(
            userId,
            gameId
        )
    }

    fun updateUserReview(userReview: String) {
        val gameUserReview = getData() ?: return
        val updatedGameUserReview = gameUserReview.withUserReview(userReview)
        executeIO(
            Dispatchers.IO,
            onBefore = { emit(ViewModelState.SuccessState(updatedGameUserReview)) },
            execute = { updateUserReviewForGameInteractor.execute(updatedGameUserReview) },
            onFail = { emit(ViewModelState.SuccessState(gameUserReview)) },
        )
    }

    fun updateUserScore(userScore: Int) {
        val gameUserReview = getData() ?: return
        val updatedGameUserScore = gameUserReview.withUserScore(userScore)
        executeIO(
            Dispatchers.IO,
            onBefore = { emit(ViewModelState.SuccessState(updatedGameUserScore)) },
            execute = { updateUserReviewForGameInteractor.execute(updatedGameUserScore) },
            onFail = { emit(ViewModelState.SuccessState(gameUserReview)) },
        )
    }



}