package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameNewsEntity

interface GameNewsRepository {
    suspend fun getNewsForGame(gameId: String): List<GameNewsEntity>
}