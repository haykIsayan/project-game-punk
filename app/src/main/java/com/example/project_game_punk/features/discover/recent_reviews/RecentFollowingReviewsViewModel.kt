package com.example.project_game_punk.features.discover.recent_reviews

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.game.GetGamesInteractor
import com.example.game_punk_domain.domain.interactors.reviews.GetUserReviewsInteractor
import com.example.game_punk_domain.domain.interactors.reviews.UpdateUserReviewForGameInteractor
import com.example.game_punk_domain.domain.interactors.user.GetUserFollowingInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecentFollowingReviewsViewModel @Inject constructor(
    private val userCache: UserCache,
    private val getUserFollowingInteractor: GetUserFollowingInteractor,
    private val getUserReviewsInteractor: GetUserReviewsInteractor,
    private val getGameInteractor: GetGamesInteractor
) : StateViewModel<List<GameUserReviewsState>, String>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: String?): List<GameUserReviewsState> {
        val userId = userCache.loadAndCacheUser()?.id ?: return emptyList()
        val userFollowing = getUserFollowingInteractor.execute(userId)
        val reviews = userFollowing.map { user ->
            val followingUserId = user.id ?: return@map null
            val review = getUserReviewsInteractor.execute(followingUserId).first()
                GameUserReviewsState(user, null, review)
        }
        val gameIds = reviews.mapNotNull { it?.review?.gameId }
        val games = getGameInteractor.execute(GameQueryModel(ids = gameIds))
//        rev
        return reviews.filterNotNull()
    }
}

data class GameUserReviewsState(
    val user: UserEntity? = null,
    val game: GameEntity? = null,
    val review: GameReviewEntity? = null,
)