package com.example.project_game_punk.data.game.idgb

import android.util.Log
import com.example.project_game_punk.data.game.idgb.api.IDGBApi
import com.example.project_game_punk.data.game.idgb.api.IDGBAuthApi
import com.example.project_game_punk.data.game.rawg.RawgApi
import com.example.project_game_punk.data.game.rawg.models.GameModel
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.models.GameQueryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class GameIDGBDataSource(
    private val clientId: String,
    private val clientSecret: String,
    private val idgbApi: IDGBApi,
    private val rawgApi: RawgApi,
    private val idgbAuthApi: IDGBAuthApi,
    private val scope: CoroutineScope
    ): GameRepository {

    private var idgbAuthModel: IDGBAuthModel? = null

    private suspend fun getAuthModel(): IDGBAuthModel {
        return idgbAuthModel ?: idgbAuthApi.authenticate(
            clientId,
            clientSecret
        )
    }

    override suspend fun getGames(gameQuery: GameQueryModel): List<GameEntity> {
        val games = withAuthenticatedHeaders { headers ->

            val fields = StringBuilder()
            if (gameQuery.query.isNotEmpty()) {
                fields.append("search \"${gameQuery.query}\";")
            }
            fields.append("fields name,cover,screenshots,artworks,slug;")
            fields.append("where category = 0;")

            idgbApi.getGames(
                headers,
                fields.toString()
            )
        }
        return applyCovers(games)
    }


    private suspend fun applyCovers(games: List<GameModel>): List<GameModel> {
        val ids = games
            .joinToString(postfix = ",") { it.id ?: "" }.let { it
            it.substring(0, it.length - 1)
        }
        val fields = StringBuilder()
        fields.append("fields url,game;")
        fields.append("where game = ($ids);")
        val covers = withAuthenticatedHeaders { headers ->
            idgbApi.getCovers(
                headers,
                fields.toString()
            )
        }
        return games.map { game ->
            val cover = covers.find { it.game == game.id }
            val updatedCover = "https:" + cover?.url?.replace("t_thumb","t_720p")
            game.copy(cover = updatedCover)
        }
    }

    private suspend fun applyScreenshots(games: List<GameModel>): List<GameModel> {
        val ids = games
            .joinToString(postfix = ",") { it.id ?: "" }.let { it
                it.substring(0, it.length - 1)
            }
        val fields = StringBuilder()
        fields.append("fields url,game;")
        fields.append("where game = ($ids);")
        val screenshots = withAuthenticatedHeaders { headers ->
            idgbApi.getScreenshots(
                headers,
                fields.toString()
            )
        }
        return games.map { game ->
            val screenshot = screenshots.find { it.game == game.id }
            val updatedScreenshot = "https:" + screenshot?.url?.replace("t_thumb","t_720p")
            game.copy(screenshots = listOf(updatedScreenshot))
        }
    }

    private suspend fun applyArtworks(games: List<GameModel>): List<GameModel> {
        val ids =
            games
            .joinToString(postfix = ",") { it.id ?: "" }.let { it
                it.substring(0, it.length - 1)
            }
        val fields = StringBuilder()
        fields.append("fields url,game,image_id;")
        fields.append("where game = ($ids);")
        fields.append("limit 20;")
        val artworks = withAuthenticatedHeaders { headers ->
            idgbApi.getArtworks(
                headers,
                fields.toString()
            )
        }
        return games.map { game ->
            val screenshot = artworks.find { it.game == game.id }
            val updatedScreenshot = "https:" + screenshot?.url?.replace("t_thumb","t_720p")
            game.copy(artworks = listOf(updatedScreenshot))
        }
    }

     override suspend fun applyBanners(games: List<GameEntity>): List<GameEntity> {
        val slugs =
//            "tomb-raider"
            games.joinToString(postfix = ",") { (it as GameModel).slug ?: "" }.let { it
            it.substring(0, it.length - 1)
        }



        val resultsAll = games.map {
            scope.async { rawgApi.getGames(search = (it as GameModel).slug) }
        }.awaitAll().map { it.results }

        val results = resultsAll.flatten()

        val gamesWithBanners = games.map { game ->
            val gameSlug = (game as GameModel).slug
            val banner = results.find { it.slug == gameSlug || (gameSlug != null && it.slug?.contains(gameSlug) == true) }?.background_image
            game.copy(banner = banner)
        }

//
//        val gamesWithBanner = games.map {
////
////            async {
////
////            }
//
//            val response = rawgApi.getGames(search = it.slug)
//            it.copy(banner = response.results.first().background_image)
//        }

//
        Log.d("Haykk",  slugs + results + gamesWithBanners + resultsAll)


        return gamesWithBanners
    }

    override suspend fun getGame(id: String): GameEntity {
        TODO("Not yet implemented")
    }

    private suspend fun <T> withAuthenticatedHeaders(
        onHeadersCreated: suspend (Map<String, String>) -> T,

    ): T {
        val idgbAuth = getAuthModel()
        val headers = mutableMapOf<String, String>().apply {
            put("Client-ID", clientId)
            put("Authorization", "Bearer ${idgbAuth.access_token}")
        }
        return onHeadersCreated.invoke(headers)
    }
}