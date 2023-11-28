package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameStoresInteractor(
    private val gameRepository: GameRepository
) {

    suspend fun execute(gameId: String): List<GameStoreEntity> {
        return gameRepository.getGameStores(gameId)
    }
}