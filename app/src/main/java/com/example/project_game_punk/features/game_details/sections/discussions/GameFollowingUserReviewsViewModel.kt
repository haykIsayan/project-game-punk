package com.example.project_game_punk.features.game_details.sections.discussions

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.game.GetUserTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.reviews.GetUserReviewForGameInteractor
import com.example.game_punk_domain.domain.interactors.reviews.GetUserReviewsInteractor
import com.example.game_punk_domain.domain.interactors.user.GetFollowingUserReviewsForGameInteractor
import com.example.game_punk_domain.domain.interactors.user.GetUserFollowingInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

@HiltViewModel
class GameFollowingUserReviewsViewModel @Inject constructor(
    private val userCache: UserCache,
    private val getUserFollowingInteractor: GetUserFollowingInteractor,
    private val getUserTrackedGamesInteractor: GetUserTrackedGamesInteractor,
    private val getUserReviewForGameInteractor: GetUserReviewForGameInteractor
) : StateViewModel<List<GameFollowingUserReviewsState>, String>() {
    override suspend fun loadData(
        param: String?
    ): List<GameFollowingUserReviewsState> {
        val gameId = param ?: return emptyList()
        val userId = userCache.userId() ?: return emptyList()
        return getUserFollowingInteractor.execute(userId).map { followingUser ->
            viewModelScope.async {
                followingUser.id?.let { followingUserId ->
                    val review = getUserReviewForGameInteractor.execute(
                        followingUserId,
                        gameId
                    ) ?: return@async null
                    GameFollowingUserReviewsState(
                        followingUser,
                        review
                    )
                }
            }
        }.awaitAll().filterNotNull()
    }
}

data class GameFollowingUserReviewsQuery(
    val userId: String,
    val gameId: String
)

data class GameFollowingUserReviewsState(
    val user: UserEntity,
//    val game: GameEntity,
    val review: GameReviewEntity
)