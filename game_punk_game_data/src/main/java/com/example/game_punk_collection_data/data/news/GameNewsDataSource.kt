package com.example.game_punk_collection_data.data.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GameNewsDataSource(
    private val steamNewsApi: SteamNewsApi,
    private val gameRepository: GameRepository
): GameNewsRepository {
    override suspend fun getNewsForGame(gameId: String): List<GameNewsEntity> {
        val steamId = gameRepository.getGameSteamId(gameId)
        val response = steamNewsApi.getNewsForGame(steamId.toInt())
        val news = response.appnews
        return news?.newsitems ?: emptyList()
    }
}