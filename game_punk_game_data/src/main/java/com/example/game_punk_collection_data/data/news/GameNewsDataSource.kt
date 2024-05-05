package com.example.game_punk_collection_data.data.news

import com.example.game_punk_collection_data.data.game.idgb.unixToFormatted
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.entity.GamingNewsEntity
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GameNewsDataSource(
    private val gamingNewsApi: GamingNewsApi,
    private val steamNewsApi: SteamNewsApi,
    private val gameRepository: GameRepository
): GameNewsRepository {

    override suspend fun getNewsForGame(gameId: String): List<GameNewsEntity> {
        val steamId = gameRepository.getGameSteamId(gameId)
        if (steamId.isEmpty()) return emptyList()
        val response = steamNewsApi.getNewsForGame(steamId.toInt())
        val news = response.appnews
        val newsItems = news?.newsitems
        val cleanedNewsItems = newsItems?.map { newsModel ->
            val alteredDescription = removeImageTag(
                newsModel.description
                    .replace("[", "<")
                    .replace("]", ">")
            )
            val cleanedDescription = alteredDescription
                .replace(Regex("\\<.*?\\>"),"")
                .trim()
            val dateUnix = newsModel.date
            newsModel.copy(
                contents = cleanedDescription,
                date = dateUnix.unixToFormatted()
            )
        }?.filter { newsModel ->
            !newsModel.author.contains(".ru")
        }
        return cleanedNewsItems ?: emptyList()
    }

    private fun removeImageTag(input: String): String {
        var newString = input
        var a = input.indexOf("<img>")
        var b = input.indexOf("</img>") + 6
        while (a != -1 && b != -1 && a <= b) {
            newString = newString.replaceRange(a, b, "")
            a = newString.indexOf("<img>")
            b = newString.indexOf("</img>") + 6
        }
        return newString
    }


    override suspend fun getGamingNews(): List<GamingNewsEntity> {
//        return
//        gamingNewsApi.getTopGamingNews(
//            "us",
////            "ign",
//            "entertainment",
//            "b824bfb98f294e4b943c170840f3e423"
//        ).articles


//        from=2024-01-29&to=2024-01-29&sortBy=popularity

        return gamingNewsApi.getGamingNews(
//            q = "game,player",
            sources = "ign,polygon",
            languages = "en",
            from = "2024-01-01",
            to = "2024-01-30",
            sortBy = "popularity",
//            searchIn = "content,description",
//            category = "entertainment",
            apiKey = "b824bfb98f294e4b943c170840f3e423"
        ).articles
    }
}