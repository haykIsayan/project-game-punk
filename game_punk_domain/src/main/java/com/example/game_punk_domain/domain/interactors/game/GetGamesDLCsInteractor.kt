package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGamesDLCsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(gameId: String): List<GameEntity> {
        return gameRepository.getGameDLCs(gameId)
    }
}