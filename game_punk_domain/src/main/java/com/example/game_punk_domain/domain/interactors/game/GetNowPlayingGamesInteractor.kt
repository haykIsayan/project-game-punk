package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameQueryModel

class GetNowPlayingGamesInteractor(
    private val applyGameMetaInteractor: ApplyGameMetaInteractor,
    private val trackedGamesCache: TrackedGamesCache,
    private val gameRepository: GameRepository
) {
    suspend fun execute(): List<GameEntity> {
        val collection = trackedGamesCache.getMainGameCollection() ?: return emptyList()
        val games = collection.games
        val nowPlayingGames = games.filter {
            it.gameExperience?.gameProgressStatus == GameProgressStatus.playing
                    || it.gameExperience?.gameProgressStatus == GameProgressStatus.replaying
        }



        val updatedGames = if (nowPlayingGames.isNotEmpty()) gameRepository.getGames(
            gameQuery = GameQueryModel(
                ids = nowPlayingGames.mapNotNull { it.id }.toList(),
                gameMetaQuery = GameMetaQueryModel(
                    platforms = true
                )
            )
        ) else emptyList()

        val cachedGames = trackedGamesCache.applyCache(updatedGames)

        return applyGameMetaInteractor.execute(
            cachedGames,
            GameMetaQueryModel()
        )
    }
}