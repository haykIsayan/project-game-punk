package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserSignUpInteractor(
    private val userCache: UserCache,
    private val trackedGamesCache: TrackedGamesCache,
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userAuthModel: UserAuthModel
    ): UserEntity {
        val user = userRepository.createUser(userAuthModel)
        userCache.loadAndCacheUser()
        trackedGamesCache.getMainGameCollection()
        return user
    }
}