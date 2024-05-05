package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserSignInInteractor(
    private val userCache: UserCache,
    private val trackedGamesCache: TrackedGamesCache,
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userAuthModel: UserAuthModel
    ): UserEntity {
        val email = userAuthModel.email
        val password = userAuthModel.password
        val user = userRepository.signIn(email, password)
        userCache.loadAndCacheUser()
        trackedGamesCache.getMainGameCollection()
        return user
    }
}
