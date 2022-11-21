package com.example.project_game_punk.domain.interactors.game_collection.tracking

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.project_game_punk.domain.models.GameModel

class TrackUntrackGameInteractor(
    private val trackedGamesCache: TrackedGamesCache,
    private val addGameToGameCollectionInteractor: AddGameToGameCollectionInteractor,
    private val removeGameFromGameCollectionInteractor: RemoveGameFromGameCollectionInteractor
) {
    suspend fun execute(game: GameModel): GameModel {
        val mainGameCollection = trackedGamesCache.getMainGameCollection()
        val updatedMainGameCollection = if (!game.isAdded)
            addGameToGameCollectionInteractor.execute(
                game,
                mainGameCollection
            )
        else removeGameFromGameCollectionInteractor.execute(
            game,
            mainGameCollection
        )
        trackedGamesCache.updateCache(updatedMainGameCollection)
        return game.copy(isAdded = !game.isAdded)
    }
}