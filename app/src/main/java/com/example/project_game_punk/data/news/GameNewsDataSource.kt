package com.example.project_game_punk.data.news

import com.example.project_game_punk.domain.entity.GameNewsEntity
import com.example.project_game_punk.domain.interfaces.GameNewsRepository

class GameNewsDataSource(
    private val newsApi: NewsApi
): GameNewsRepository {
    override fun getGameNews(): List<GameNewsEntity> {
        return emptyList()
    }
}