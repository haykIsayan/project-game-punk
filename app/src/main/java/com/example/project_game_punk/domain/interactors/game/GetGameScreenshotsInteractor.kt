package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.interfaces.GameRepository

class GetGameScreenshotsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(
        id: String
    ): List<String> {
        return gameRepository.getScreenshots(id)
    }
}