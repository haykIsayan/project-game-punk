package com.example.game_punk_domain.domain.interactors.game_collection

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository

class GetGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
) {
    suspend fun execute(id: String, userId: String): GameCollectionEntity? {
        return gameCollectionRepository.getGameCollection(id, userId)
    }
}