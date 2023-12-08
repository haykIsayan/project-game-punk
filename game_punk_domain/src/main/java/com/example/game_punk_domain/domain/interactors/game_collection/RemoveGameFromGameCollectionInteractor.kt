package com.example.game_punk_domain.domain.interactors.game_collection

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository

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