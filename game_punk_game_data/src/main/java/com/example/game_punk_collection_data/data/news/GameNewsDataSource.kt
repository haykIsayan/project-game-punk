package com.example.game_punk_collection_data.data.news

import androidx.core.text.HtmlCompat
import com.example.game_punk_collection_data.data.game.idgb.unixToFormatted
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
        val newsItems = news?.newsitems
        val cleanedNewsItems = newsItems?.map { newsModel ->
            val cleanedDescription = HtmlCompat.fromHtml(
                newsModel.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
            val dateUnix = newsModel.date
            newsModel.copy(
                contents = cleanedDescription,
                date = dateUnix.unixToFormatted()
            )
        }
        return cleanedNewsItems ?: emptyList()
    }
}