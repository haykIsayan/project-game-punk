package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameMetaQueryModel
import com.example.project_game_punk.domain.interfaces.GameRepository

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