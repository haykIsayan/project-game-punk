package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.entity.GameProgress

class GetNowPlayingGamesInteractor(
    private val applyGameMetaInteractor: ApplyGameMetaInteractor,
    private val trackedGamesCache: TrackedGamesCache
) {
    suspend fun execute(): List<GameEntity> {
        val collection = trackedGamesCache.getMainGameCollection()
        val games = collection.games
        val nowPlayingGames = games.filter {
            it.gameProgress == GameProgress.PlayingGameProgress
                    || it.gameProgress == GameProgress.ReplayingGameProgress
        }
        return applyGameMetaInteractor.execute(
            nowPlayingGames,
            GameMetaQueryModel(banner = true)
        )
    }
}