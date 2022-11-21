package com.example.project_game_punk.domain.interfaces

import com.example.project_game_punk.domain.entity.GameNewsEntity

interface GameNewsRepository {
    fun getGameNews(): List<GameNewsEntity>
}