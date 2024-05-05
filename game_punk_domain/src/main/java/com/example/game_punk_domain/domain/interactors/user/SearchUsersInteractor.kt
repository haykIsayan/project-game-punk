package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository

class SearchUsersInteractor(
    private val userRepository: UserRepository
) {

    suspend fun execute(searchQuery: String): List<UserEntity>{
        return userRepository.getUserByDisplayName(searchQuery)
    }
}