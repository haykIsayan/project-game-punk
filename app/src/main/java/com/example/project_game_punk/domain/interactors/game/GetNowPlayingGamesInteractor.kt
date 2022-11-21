package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.models.GameModel

class GetNowPlayingGamesInteractor(
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(): List<GameModel> {
        val collection = trackedGamesCache.getMainGameCollection()
        return collection.games.filter {
            it.gameProgress == GameProgress.PlayingGameProgress
                    || it.gameProgress == GameProgress.ReplayingGameProgress
        }
    }
}