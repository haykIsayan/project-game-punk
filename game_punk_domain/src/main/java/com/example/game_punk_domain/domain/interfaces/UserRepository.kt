package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity

interface UserRepository {
    suspend fun createUser(userAuthModel: UserAuthModel): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity

    suspend fun getUserId(userId: String): UserEntity

    suspend fun setUserSession(userId: String?)

    suspend fun getUserSession(): String
}