package com.example.game_punk_collection_data.data.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository

class GameNewsDataSource(
    private val newsApi: NewsApi
): GameNewsRepository {
    override fun getGameNews(): List<GameNewsEntity> {
        return emptyList()
    }
}