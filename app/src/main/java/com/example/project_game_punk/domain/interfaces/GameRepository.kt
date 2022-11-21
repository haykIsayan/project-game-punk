package com.example.project_game_punk.domain.interfaces

import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameQueryModel

interface GameRepository {
    suspend fun getGames(gameQuery: GameQueryModel): List<GameModel>

    suspend fun getGame(id: String): GameModel
}