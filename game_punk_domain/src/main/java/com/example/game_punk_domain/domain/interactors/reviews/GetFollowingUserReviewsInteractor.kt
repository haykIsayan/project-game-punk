package com.example.game_punk_domain.domain.interactors.reviews

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interactors.user.GetUserFollowingInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.game_punk_domain.domain.interfaces.ReviewRepository
import com.example.game_punk_domain.domain.models.ReviewQueryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

class GetFollowingUserReviewsInteractor(
    private val userCache: UserCache,
    private val getUserFollowingInteractor: GetUserFollowingInteractor,
    private val reviewRepository: ReviewRepository
) {
    suspend fun execute(scope: CoroutineScope): List<GameReviewEntity> {
        val userId = userCache.userId() ?: return emptyList()
        val followers = getUserFollowingInteractor.execute(userId)
        val followerReviews = followers.map { user ->
            val reviews = scope.async {
                reviewRepository.getReviews(
                    ReviewQueryModel(
                        userIds = listOf(userId),
                        gameId = "",
                        limit = 3
                    )
                )
            }
            reviews
        }.awaitAll().toList()
        return followerReviews.flatten()
    }
}