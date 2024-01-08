package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.interfaces.UserRepository

class IsUserSessionActiveInteractor(
    private val userRepository: UserRepository
) {
    suspend fun execute(): Boolean {
        return userRepository.isUserSessionActive()
    }
}