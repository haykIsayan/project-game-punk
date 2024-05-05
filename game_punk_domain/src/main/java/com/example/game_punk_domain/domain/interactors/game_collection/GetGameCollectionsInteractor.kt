package com.example.game_punk_domain.domain.interactors.game_collection

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository

class GetGameCollectionsInteractor(
    private val gameCollectionRepository: GameCollectionRepository
) {

    suspend fun execute(userId: String): List<GameCollectionEntity> {
        return gameCollectionRepository.getGameCollections(userId).filter { gameCollection ->
            gameCollection.id != "main"
        }
    }
}