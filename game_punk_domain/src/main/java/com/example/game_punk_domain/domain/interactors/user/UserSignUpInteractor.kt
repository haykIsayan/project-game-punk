package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class UserSignUpInteractor(
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userAuthModel: UserAuthModel
    ): UserEntity {
        return userRepository.createUser(userAuthModel)
    }
}