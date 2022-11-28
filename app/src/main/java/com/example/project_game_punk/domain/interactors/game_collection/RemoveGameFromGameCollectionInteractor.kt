package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository

class RemoveGameFromGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository
) {
    suspend fun execute(game: GameEntity, gameCollection: GameCollectionEntity): GameCollectionEntity {
        val games = gameCollection.games
        val updatedGames = games.toMutableList().apply { remove(game) }
        val updatedGameCollection = gameCollection.withGames(updatedGames)
        gameCollectionRepository.updateGameCollection(updatedGameCollection)
        return updatedGameCollection
    }
}