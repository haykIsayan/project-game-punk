package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.interfaces.UserRepository

class UnfollowUserInteractor(
    private val userCache: UserCache,
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userId: String,
        userIdToUnfollow: String
    ) {
        val user = userCache.loadAndCacheUser()
        val updatedFollowing =
            user?.following?.toMutableList()?.apply { remove(userIdToUnfollow) } ?: return
        val updatedUser = user.withFollowing(updatedFollowing)
        userCache.updateUser(updatedUser)
        userRepository.unfollowUser(
            userId,
            userIdToUnfollow
        )
    }
}