package com.example.project_game_punk.data.game.idgb

import android.util.Log
import com.example.project_game_punk.data.game.idgb.api.IDGBApi
import com.example.project_game_punk.data.game.idgb.api.IDGBAuthApi
import com.example.project_game_punk.data.game.rawg.models.GameModel
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.models.GameQueryModel

class GameIDGBDataSource(
    private val clientId: String,
    private val clientSecret: String,
    private val idgbApi: IDGBApi,
    private val idgbAuthApi: IDGBAuthApi
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
            fields.append("fields name,cover,screenshots;")
            fields.append("where category = 0;")

            idgbApi.getGames(
                headers,
                fields.toString()
            )
        }
        val gamesWithCovers = applyCovers(games)
        val gamesWithScreenshots = applyScreenshots(gamesWithCovers)
        return applyArtworks(gamesWithScreenshots)
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
        Log.d("Haykk", "bruuuuuh $screenshots")
//        return games
        return games.map { game ->
            val screenshot = screenshots.find { it.game == game.id }
            val updatedScreenshot = "https:" + screenshot?.url?.replace("t_thumb","t_720p")
            game.copy(screenshots = listOf(updatedScreenshot))
        }
    }

    private suspend fun applyArtworks(games: List<GameModel>): List<GameModel> {
        val ids = games
            .joinToString(postfix = ",") { it.id ?: "" }.let { it
                it.substring(0, it.length - 1)
            }
        val fields = StringBuilder()
        fields.append("fields url,game;")
        fields.append("where game = ($ids);")
        val screenshots = withAuthenticatedHeaders { headers ->
            idgbApi.getArtworks(
                headers,
                fields.toString()
            )
        }
        Log.d("Haykk", "bruuuuuh $screenshots")
//        return games
        return games.map { game ->
            val screenshot = screenshots.find { it.game == game.id }
            val updatedScreenshot = "https:" + screenshot?.url?.replace("t_thumb","t_720p")
            game.copy(artworks = listOf(updatedScreenshot))
        }
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