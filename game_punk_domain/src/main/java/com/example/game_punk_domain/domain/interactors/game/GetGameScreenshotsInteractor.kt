package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameScreenshotsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(
        id: String
    ): List<String> {
        return gameRepository.getScreenshots(id)
    }
}