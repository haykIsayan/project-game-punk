package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameInteractor(
    private val applyGameMetaInteractor: ApplyGameMetaInteractor,
    private val gameRepository: GameRepository,
    private val trackedGamesCache: TrackedGamesCache,
) {
    suspend fun execute(id: String): GameEntity {
        val gameMetaQuery = GameMetaQueryModel(
            banner = true,
            synopsis = true,
            score = true
        )
        val game = gameRepository.getGame(id, gameMetaQuery)
        val games = applyGameMetaInteractor.execute(listOf(game), gameMetaQuery)
        return trackedGamesCache.applyCache(games).first()
    }
}