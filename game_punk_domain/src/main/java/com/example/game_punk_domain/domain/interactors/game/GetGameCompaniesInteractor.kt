package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.game.GameCompanyEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameCompaniesInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(gameId: String): List<GameCompanyEntity> {
        return gameRepository.getGameCompanies(gameId)
    }
}