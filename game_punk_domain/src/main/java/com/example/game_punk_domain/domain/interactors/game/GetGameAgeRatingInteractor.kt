package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.game.GameAgeRatingEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameAgeRatingInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(gameId: String): GameAgeRatingEntity {
        return gameRepository.getGameAgeRating(gameId)
    }
}