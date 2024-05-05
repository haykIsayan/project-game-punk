package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.interfaces.UserRepository

class FollowUserInteractor(
    private val userCache: UserCache,
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userId: String,
        userIdToFollow: String
    ) {
        val user = userCache.loadAndCacheUser()
        val updatedFollowing =
            user?.following?.toMutableList()?.apply { add(userIdToFollow) } ?: return
        val updatedUser = user.withFollowing(updatedFollowing)
        userCache.updateUser(updatedUser)
        userRepository.followUser(
            userId,
            userIdToFollow
        )
    }
}