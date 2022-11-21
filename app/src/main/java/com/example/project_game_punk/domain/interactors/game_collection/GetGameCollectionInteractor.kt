package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.models.GameCollectionModel

class GetGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
) {
    suspend fun execute(id: String): GameCollectionModel? {
        return gameCollectionRepository.getGameCollection(id)
    }
}