package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserSignInInteractor(
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userAuthModel: UserAuthModel
    ): UserEntity {
        val email = userAuthModel.email
        val password = userAuthModel.password
        return userRepository.signIn(email, password)
    }
}
