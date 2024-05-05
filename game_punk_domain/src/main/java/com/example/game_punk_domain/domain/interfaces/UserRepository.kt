package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity

interface UserRepository {
    suspend fun createUser(userAuthModel: UserAuthModel): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity

    suspend fun signOut()

    suspend fun getCurrentUser(): UserEntity

    suspend fun getUserById(userId: String): UserEntity

    suspend fun getUserByDisplayName(displayName: String): List<UserEntity>

    suspend fun followUser(userId: String, userIdToFollow: String)

    suspend fun unfollowUser(userId: String, userIdToUnfollow: String)

    suspend fun getUserFollowers(userId: String): List<UserEntity>

    suspend fun getUserFollowing(userId: String): List<UserEntity>

    suspend fun getFollowingUserReviewsForGame(
        userId: String,
        gameId: String
    ): List<GameExperienceEntity>

    suspend fun isUserSessionActive(): Boolean
}