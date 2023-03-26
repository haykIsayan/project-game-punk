package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameMetaQueryModel
import com.example.project_game_punk.domain.interfaces.GameRepository

class GetGameInteractor(
    private val applyGameMetaInteractor: ApplyGameMetaInteractor,
    private val gameRepository: GameRepository,
    private val trackedGamesCache: TrackedGamesCache,
) {
    suspend fun execute(id: String): GameEntity {
        val gameMetaQuery = GameMetaQueryModel(banner = true, synopsis = true)
        val game = gameRepository.getGame(id, gameMetaQuery)
        val games = applyGameMetaInteractor.execute(listOf(game), gameMetaQuery)
        return trackedGamesCache.applyCache(games).first()
    }
}