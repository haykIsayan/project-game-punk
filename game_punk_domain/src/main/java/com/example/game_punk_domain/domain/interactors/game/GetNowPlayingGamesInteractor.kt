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
    private val getUserTrackedGamesInteractor: GetUserTrackedGamesInteractor,
    private val gameRepository: GameRepository
) {
    suspend fun execute(userId: String?): List<GameEntity> {
        val games = getUserTrackedGamesInteractor.execute(userId)    /*trackedGamesCache.getMainGameCollection()*/ ?: return emptyList()
        /*collection.games*/
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

        val gamesWithExperience = updatedGames.mapNotNull { game ->
            nowPlayingGames.find { nowPlayingGame ->
                nowPlayingGame.id == game.id
            }?.gameExperience?.let { experience ->
                game.updateGameExperience(experience)
            }

        }
        return gamesWithExperience
    }
}