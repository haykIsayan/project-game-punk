package com.example.game_punk_domain.domain.interactors.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository

class GetNewsForGameInteractor(
    private val gameNewsRepository: GameNewsRepository
) {
    suspend fun execute(gameId: String): List<GameNewsEntity> {
        return gameNewsRepository.getNewsForGame(gameId)
    }
}