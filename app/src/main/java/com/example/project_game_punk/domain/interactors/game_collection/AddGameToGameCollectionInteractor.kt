package com.example.project_game_punk.domain.interactors.game_collection

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.data.game.rawg.models.GameModel

class AddGameToGameCollectionInteractor(
    private val gameCollectionRepository: GameCollectionRepository
) {
    suspend fun execute(game: GameModel, gameCollection: GameCollectionEntity): GameCollectionEntity {
        val games = gameCollection.games
        val updatedGames = games.toMutableList().apply { add(game) }
        val updatedGameCollection = gameCollection.withGames(updatedGames)
        gameCollectionRepository.updateGameCollection(updatedGameCollection)
        return updatedGameCollection
    }
}