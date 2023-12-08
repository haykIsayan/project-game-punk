package com.example.game_punk_domain.domain.interactors.game_collection

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository

class CreateGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
) {
    suspend fun execute(gameCollection: GameCollectionEntity): GameCollectionEntity {
        gameCollectionRepository.createGameCollection(gameCollection)
        return gameCollection
    }
}