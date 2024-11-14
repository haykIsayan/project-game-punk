package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.game.GameAchievementEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameAchievementsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(gameId: String): List<GameAchievementEntity> {
        return gameRepository.getAchievements(gameId)
    }
}