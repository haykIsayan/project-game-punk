package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor

class GetUserTrackedGamesInteractor(
    private val trackedGamesCache: TrackedGamesCache,
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
) {

    suspend fun execute(userId: String?): List<GameEntity> {
        return userId?.let {
            getGameCollectionInteractor.execute(id = "main", userId = userId)?.games
        } ?: trackedGamesCache.getMainGameCollection()?.games ?: emptyList()
    }
}