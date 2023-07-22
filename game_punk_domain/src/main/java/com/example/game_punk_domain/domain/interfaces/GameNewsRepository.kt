package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameNewsEntity

interface GameNewsRepository {
    fun getGameNews(): List<GameNewsEntity>
}