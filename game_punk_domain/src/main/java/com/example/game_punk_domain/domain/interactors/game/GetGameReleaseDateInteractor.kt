package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameReleaseDateInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(gameId: String): String {
        return gameRepository.getGameReleaseDate(gameId)
    }
}