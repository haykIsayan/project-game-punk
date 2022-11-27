package com.example.project_game_punk.domain.interfaces

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.models.GameQueryModel

interface GameRepository {
    suspend fun getGames(gameQuery: GameQueryModel): List<GameEntity>

    suspend fun getGame(id: String): GameEntity
}