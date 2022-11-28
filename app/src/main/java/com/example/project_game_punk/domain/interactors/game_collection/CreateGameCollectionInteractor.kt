package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository

class CreateGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository,
) {
    suspend fun execute(gameCollection: GameCollectionEntity): GameCollectionEntity {
        gameCollectionRepository.createGameCollection(gameCollection)
        return gameCollection
    }
}