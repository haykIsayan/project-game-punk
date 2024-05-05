package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserCache(
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val isUserSessionActiveInteractor: IsUserSessionActiveInteractor
) {

    private var user: UserEntity? = null

    fun userId() = user?.id

    suspend fun loadAndCacheUser(): UserEntity? {
        if (user == null) {
            user = getCurrentUserInteractor.execute()
        }
        return user
    }

    fun updateUser(updatedUser: UserEntity) {
        user = updatedUser
    }

    suspend fun isActiveUserSession(): Boolean {
        return isUserSessionActiveInteractor.execute()
    }

    suspend fun clearCache() {
        user = null
    }

}