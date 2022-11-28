package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository

class GetGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
) {
    suspend fun execute(id: String): GameCollectionEntity? {
        return gameCollectionRepository.getGameCollection(id)
    }
}