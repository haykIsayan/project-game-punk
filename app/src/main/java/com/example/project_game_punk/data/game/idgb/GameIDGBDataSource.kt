package com.example.project_game_punk.data.game.idgb

import android.util.Log
import com.example.project_game_punk.data.game.idgb.api.IDGBApi
import com.example.project_game_punk.data.game.idgb.api.IDGBAuthApi
import com.example.project_game_punk.data.game.idgb.api.TwitchApi
import com.example.project_game_punk.data.game.rawg.RawgApi
import com.example.project_game_punk.data.game.rawg.models.GameModel
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameMetaQueryModel
import com.example.project_game_punk.domain.entity.GamePlatformEntity
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.models.GameFilter
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort
import com.example.project_game_punk.features.common.commaSeparated
import com.example.project_game_punk.features.common.dateToUnix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class GameIDGBDataSource(
    private val clientId: String,
    private val clientSecret: String,
    private val idgbApi: IDGBApi,
    private val rawgApi: RawgApi,
    private val idgbAuthApi: IDGBAuthApi,
    private val twitchApi: TwitchApi,
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
            val ids = when {
                gameQuery.filter == GameFilter.trending -> {
                    val twitchResponse = twitchApi.getTopGamesOnTwitch(headers)
                    twitchResponse.data.filter { !it.igdb_id.isNullOrEmpty() }
                        .commaSeparated {
                            it.igdb_id ?: ""
                        }
                }
                gameQuery.ids.isNotEmpty() -> {
                    gameQuery.ids.commaSeparated { it }
                }
                else -> {
                    null
                }
            }
            if (gameQuery.query.isNotEmpty()) {
                fields.append("search \"${gameQuery.query}\";")
            }
            fields.append("fields name,cover,slug,follows,rating${if (gameQuery.gameMetaQuery.synopsis) ",summary" else ""}${if (gameQuery.gameMetaQuery.platforms) ",platforms" else ""}${if (gameQuery.gameMetaQuery.screenshots) ",screenshots" else ""};")
            fields.append(
                "where category = 0 " +
                        (if (!ids.isNullOrEmpty())  "& id = ($ids)" else "" ) +
                        "${if (gameQuery.filter == GameFilter.highestRated) "& rating > 40" else ""} " +
                        (if (gameQuery.dateRangeStart.isNotEmpty()) "& first_release_date >= 16405920000" else "") +
                        (if (gameQuery.dateRangeEnd.isNotEmpty()) "& first_release_date <= ${gameQuery.dateRangeEnd.dateToUnix()}" else "") +
                        ";"
            )
            when (gameQuery.sort) {
                GameSort.trending -> "follows"
                GameSort.highestRated -> "rating"
                GameSort.recent -> "first_release_date"
                else -> null
            }?.let { sort ->
                fields.append("sort $sort desc;")
            }

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
            val updatedCover = "https:" + cover?.url?.replace(
                "t_thumb",
                "t_720p"
            )
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
            val updatedScreenshot = "https:" + screenshot?.url?.replace(
                "t_thumb",
                "t_720p"
            )
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
        val resultsAll = games.map {
            scope.async { rawgApi.getGames(search = (it as GameModel).slug) }
        }.awaitAll().map { it.results }
        val results = resultsAll.flatten()
        val gamesWithBanners = games.map { game ->
            val gameSlug = (game as GameModel).slug
            val banner = results.find {
                it.slug == gameSlug
                        || (gameSlug != null && it.slug?.contains(gameSlug) == true)
                        || gameSlug?.contains(it.slug ?: "") == true
            }?.background_image
            game.copy(banner = banner)
        }
        return gamesWithBanners
    }

    override suspend fun getGamePlatforms(id: String): List<GamePlatformEntity> {
        val game = getGame(
            id = id,
            gameMetaQuery = GameMetaQueryModel(
                platforms = true,
            )
        ) as GameModel

        val platformIds = game.platforms?.joinToString(",").let { it
            it?.substring(0, it.length - 1)
        }
        val platforms = withAuthenticatedHeaders { headers ->
            idgbApi.getPlatforms(headers, "fields platform_logo, name, slug; where id = ($platformIds);")
        }

        val platformLogoIds = platforms.filter { it.platform_logo != null }.joinToString { platform -> platform.platform_logo ?: "" }.let { it
            it.substring(0, it.length - 1)
        }

        val fields = StringBuilder()
        fields.append("fields alpha_channel,animated,checksum,height,image_id,url,width;")
        fields.append("where id = ($platformLogoIds);")

        val platformLogos =  withAuthenticatedHeaders { headers ->
            idgbApi.getPlatformLogos(
                headers, fields.toString())
        }
        Log.d("Haykk " , "adsf$game")
        Log.d("Haykk " , "adsf$platformLogos")
        Log.d("Haykk " , "adsf$platforms")

        return platforms.map { platform ->
            object : GamePlatformEntity {
                override val name: String
                    get() = platform.name
                override val icon: String
                    get() = platformLogos.find { platformLogo ->
                        platformLogo.id == platform.platform_logo
                    }?.url ?: ""
            }
        }
    }

    override suspend fun getScreenshots(id: String): List<String> {

//
//        val game = getGame(
//            id = id,
//            gameMetaQuery = GameMetaQueryModel(
//                screenshots = true,
//            )
//        ) as GameModel

//        val screenshotIds = game.screenshots?.joinToString(",").let { it
//            it?.substring(0, it.length - 1)
//        }

        val body = StringBuilder()
            .append("fields image_id,url;")
            .append("where game = $id;")
            //.append("where id = ($screenshotIds);")

        val screenshots = withAuthenticatedHeaders { headers ->
            idgbApi.getScreenshots(headers, body.toString())
        }

//        Log.d("Haykk", screenshotIds.toString())
        Log.d("Haykk", screenshots.toString())
        return screenshots.map { screenshot -> screenshot.url ?: "" }.filter { it.isNotEmpty() }.map { screenshot ->
            "https:" + screenshot?.replace("t_thumb","t_720p")
        }
    }

    override suspend fun getGameImages(id: String): List<String> {
        return emptyList()
    }

    override suspend fun getGame(id: String, gameMetaQuery: GameMetaQueryModel): GameEntity {
        return getGames(GameQueryModel(ids = listOf(id), gameMetaQuery = gameMetaQuery)).first()
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