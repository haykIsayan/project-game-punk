package com.example.game_punk_domain.domain.interactors

import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetAllAvailableGamePlatformsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(): List<GamePlatformEntity> {
        return gameRepository.getAllGamePlatforms()
    }
}