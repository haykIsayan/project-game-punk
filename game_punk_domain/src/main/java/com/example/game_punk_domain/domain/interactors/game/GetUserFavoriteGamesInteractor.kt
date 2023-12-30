package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity

class GetUserFavoriteGamesInteractor(
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(): List<GameEntity> {
        return trackedGamesCache.getMainGameCollection()?.games?.filter {
            it.gameExperience?.favorite == true
        } ?: emptyList()
    }
}