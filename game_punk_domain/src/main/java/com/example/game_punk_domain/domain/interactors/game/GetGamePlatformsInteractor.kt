package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGamePlatformsInteractor(
    private val gameRepository: GameRepository
){
    suspend fun execute(id: String): List<GamePlatformEntity> {
        return gameRepository.getGamePlatforms(id)
    }
}