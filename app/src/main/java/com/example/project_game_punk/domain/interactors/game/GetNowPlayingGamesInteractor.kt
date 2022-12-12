package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameMetaQueryModel
import com.example.project_game_punk.domain.entity.GameProgress

class GetNowPlayingGamesInteractor(
    private val applyGameMetaInteractor: ApplyGameMetaInteractor,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(): List<GameEntity> {
        val collection = trackedGamesCache.getMainGameCollection()
        val games = collection.games
        val gamesWithMeta = applyGameMetaInteractor.execute(
            games,
            GameMetaQueryModel(banner = true)
        )
        return gamesWithMeta.filter {
            it.gameProgress == GameProgress.PlayingGameProgress
                    || it.gameProgress == GameProgress.ReplayingGameProgress
        }

    }
}