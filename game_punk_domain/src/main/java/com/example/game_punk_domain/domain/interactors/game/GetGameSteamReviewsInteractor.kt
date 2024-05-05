package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameSteamReviewEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameSteamReviewsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(): List<GameSteamReviewEntity> {
        return emptyList()
    }
}