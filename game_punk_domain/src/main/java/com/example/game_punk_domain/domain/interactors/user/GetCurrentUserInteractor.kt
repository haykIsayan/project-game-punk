package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class GetCurrentUserInteractor(
    private val userRepository: UserRepository
) {
    suspend fun execute(): UserEntity {
        return userRepository.getUser("")
    }
}