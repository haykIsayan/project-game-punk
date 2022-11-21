package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.models.GameCollectionModel
import com.example.project_game_punk.domain.models.GameModel

class AddGameToGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository
) {
    suspend fun execute(game: GameModel, gameCollection: GameCollectionModel): GameCollectionModel {
        val games = gameCollection.games
        val updatedGames = games.toMutableList().apply { add(game) }
        val updatedGameCollection = gameCollection.copy(games = updatedGames)
        gameCollectionRepository.updateGameCollection(updatedGameCollection)
        return updatedGameCollection
    }
}