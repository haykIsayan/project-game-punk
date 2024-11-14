package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetExcitedAndInterestedGamesInteractor(
    private val trackedGamesCache: TrackedGamesCache,
    private val gameRepository: GameRepository
) {
    suspend fun execute(): List<GameEntity> {
        val trackedGames = trackedGamesCache.getMainGameCollection()?.games ?: emptyList()


//        gameRepository.getGameReleaseDate()



        return trackedGames.filter { game ->
            game.gameExperience?.gameProgressStatus == GameProgressStatus.excited
        }
    }
}