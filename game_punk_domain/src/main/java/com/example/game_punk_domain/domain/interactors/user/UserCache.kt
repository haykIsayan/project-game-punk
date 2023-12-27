package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserCache(
    private val userRepository: UserRepository,
    private val getUserInteractor: GetUserInteractor
) {

    private var userId: String? = null

    private var user: UserEntity? = null

    suspend fun setUserId(userId: String) {
        userRepository.setUserSession(userId)
        this.userId = userId
    }

    suspend fun loadAndCacheUser(): UserEntity? {
        if (userId == null) {
            userId = userRepository.getUserSession()
        }
        user = userId?.let {
            getUserInteractor.execute(it)
        }
        return user
    }

    suspend fun isActiveUserSession(): Boolean {
        return userRepository.getUserSession().isNotEmpty()
    }

    suspend fun clearCache() {
        userId = null
        user = null
        userRepository.setUserSession(null)
    }

}