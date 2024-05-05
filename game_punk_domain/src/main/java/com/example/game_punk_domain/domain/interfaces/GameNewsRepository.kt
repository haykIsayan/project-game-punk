package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.entity.GamingNewsEntity

interface GameNewsRepository {
    suspend fun getNewsForGame(gameId: String): List<GameNewsEntity>


    suspend fun getGamingNews(): List<GamingNewsEntity>
}