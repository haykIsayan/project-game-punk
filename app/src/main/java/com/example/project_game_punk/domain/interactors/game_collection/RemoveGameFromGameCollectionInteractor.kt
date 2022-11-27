package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.models.GameCollectionModel

class RemoveGameFromGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository
) {
    suspend fun execute(game: GameEntity, gameCollection: GameCollectionModel): GameCollectionModel {
        val games = gameCollection.games
        val updatedGames = games.toMutableList().apply { remove(game) }
        val updatedGameCollection = gameCollection.copy(games = updatedGames)
        gameCollectionRepository.updateGameCollection(updatedGameCollection)
        return updatedGameCollection
    }
}