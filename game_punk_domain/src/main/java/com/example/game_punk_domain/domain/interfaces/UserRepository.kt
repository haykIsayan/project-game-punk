package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity

interface UserRepository {
    suspend fun createUser(userAuthModel: UserAuthModel): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity

    suspend fun signOut()

    suspend fun getUser(userId: String): UserEntity

    suspend fun isUserSessionActive(): Boolean
}