package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.entity.GamePlatformEntity
import com.example.project_game_punk.domain.interfaces.GameRepository

class GetGamePlatformsInteractor(
    private val gameRepository: GameRepository
){
    suspend fun execute(id: String): List<GamePlatformEntity> {
        return gameRepository.getGamePlatforms(id)
    }
}