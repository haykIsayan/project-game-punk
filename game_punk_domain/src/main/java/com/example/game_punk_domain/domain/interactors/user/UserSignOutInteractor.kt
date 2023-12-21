package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserSignOutInteractor(
    private val userCache: UserCache,
    private val trackedGamesCache: TrackedGamesCache,
    private val userRepository: UserRepository
) {

    suspend fun execute() {
        userCache.clearCache()
        trackedGamesCache.clearCache()
    }

}