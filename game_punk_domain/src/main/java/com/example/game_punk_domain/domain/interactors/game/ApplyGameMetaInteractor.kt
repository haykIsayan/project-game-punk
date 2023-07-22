package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.interfaces.GameRepository

class ApplyGameMetaInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(
        games: List<GameEntity>,
        gameMetaQuery: GameMetaQueryModel
    ): List<GameEntity> {
        val updatedGames = if (gameMetaQuery.banner) {
            gameRepository.applyBanners(games)
        } else {
            games
        }
        return  updatedGames
    }

}