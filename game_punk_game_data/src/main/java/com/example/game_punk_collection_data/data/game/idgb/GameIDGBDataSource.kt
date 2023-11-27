package com.example.game_punk_collection_data.data.game.idgb

import android.os.Build
import com.example.game_punk_collection_data.data.game.idgb.api.IDGBApi
import com.example.game_punk_collection_data.data.game.idgb.api.IDGBAuthApi
import com.example.game_punk_collection_data.data.game.twitch.TwitchApi
import com.example.game_punk_collection_data.data.game.rawg.RawgApi
import com.example.game_punk_collection_data.data.game.rawg.models.GameModel
import com.example.game_punk_collection_data.data.game.rawg.models.PlatformModel
import com.example.game_punk_domain.domain.entity.*
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

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
            val platformIds = gameQuery.platforms.map {
                it.id
            }.commaSeparated { it }
            val genreIds = gameQuery.genres.map {
                it.id
            }.commaSeparated { it }
            if (gameQuery.query.isNotEmpty()) {
                fields.append("search \"${gameQuery.query}\";")
            }
//            16405920000
            fields.append("fields name,cover,slug,follows,rating" +
                    (if (gameQuery.gameMetaQuery.synopsis) ",summary" else "") +
                    (if (gameQuery.gameMetaQuery.platforms) ",platforms" else "") +
                    (if (gameQuery.gameMetaQuery.genres) ",genres" else "") +
                    (if (gameQuery.gameMetaQuery.screenshots) ",screenshots" else "") +
                    (if (gameQuery.gameMetaQuery.steamId) ",websites" else "") +
                    (if(gameQuery.gameMetaQuery.similarGames)",similar_games" else "") +
                    (if(gameQuery.gameMetaQuery.ageRating)",age_ratings" else "") +
                    "${if(gameQuery.gameMetaQuery.score)",aggregated_rating" else ""};"
            )
            fields.append(
                "where category = 0 " +
                        (if (!ids.isNullOrEmpty())  "& id = ($ids)" else "" ) +
                        (if (platformIds.isNotEmpty())  "& platforms = ($platformIds)" else "" ) +
                        (if (genreIds.isNotEmpty())  "& genres = ($genreIds)" else "" ) +
                        "${if (gameQuery.filter == GameFilter.highestRated) "& rating > 40" else ""} " +
                        (if (gameQuery.dateRangeStart.isNotEmpty()) "& first_release_date >= 1668585600" else "") +
//                        (if (gameQuery.dateRangeEnd.isNotEmpty()) "& first_release_date <= ${gameQuery.dateRangeEnd.dateToUnix()}" else "") +
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
            ).filter {
                it.id != "206893"
            }
        }
        return applyCovers(games)
    }

    override suspend fun getGameAgeRating(gameId: String): GameAgeRatingEntity {

        val gameWithAgeRatingsIds = (getGame(gameId, GameMetaQueryModel(ageRating = true)) as? GameModel)





        val ageRatingIds = gameWithAgeRatingsIds?.age_ratings?.joinToString(",").let {
            it?.substring(0, it.length - 1)
        } ?: ""







        val ageRatings = withAuthenticatedHeaders { headers ->
            val fields = StringBuilder()
            fields.append("fields rating_cover_url,synopsis;")
            fields.append("where category = (1) & id = ($ageRatingIds);")







            idgbApi.getAgeRatings(headers, fields.toString())
        }
        println(ageRatings.toString())
        return ageRatings.first()
    }

    override suspend fun getSimilarGames(gameId: String): List<GameEntity> {
//        val fields = StringBuilder()
//        fields.append("fields category,checksum,countries,created_at,game,media,name,platform,uid,updated_at,url,year;")
//        fields.append("where game = ($gameId) & category = (1,26,36,54,11,28,31);")
//
//        val external = withAuthenticatedHeaders { headers ->
//            idgbApi.getExternalGames(headers, fields.toString())
//        }
//
//        println(external)


        /// todo all good here




       val gameWithSimilarGameIds = getGame(gameId, GameMetaQueryModel(similarGames = true))
        return (gameWithSimilarGameIds as? GameModel)?.similar_games?.let { similarGameIds ->
            getGames(
                gameQuery = GameQueryModel(
                    filter = GameFilter.highestRated,
                    sort = GameSort.trending,
                    ids = similarGameIds,

                    gameMetaQuery = GameMetaQueryModel(
                        cover = true
                    )
                )
            )
        } ?: emptyList()
    }

    private fun extractSteamId(steamWebsite: String): String {
        return steamWebsite.let {
            val elements = it.split("/")
            val i = elements.indexOf("app")
            elements[i + 1]
        }
    }

    private suspend fun applyCovers(games: List<GameModel>): List<GameModel> {
        val ids = games
            .joinToString(postfix = ",") { it.id ?: "" }.let {
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
            .joinToString(postfix = ",") { it.id ?: "" }.let {
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

        println(game)
        val platformIds = game.platforms?.joinToString(",").let {
            if (it?.get(it.length - 1) == ',') {
                it.substring(0, it.length - 1)
            } else {
                it
            }
        }
        println(platformIds)
        val platforms = withAuthenticatedHeaders { headers ->
            idgbApi.getPlatforms(headers, "fields platform_logo, name, slug; where id = ($platformIds);")
        }

        println(platforms)

        val platformLogoIds = platforms.filter { it.platform_logo != null }.joinToString { platform -> platform.platform_logo ?: "" }.let {
            if (it.get(it.length - 1) == ',') {
                it.substring(0, it.length - 1)
            } else {
                it
            }
        }

        val fields = StringBuilder()
        fields.append("fields alpha_channel,animated,checksum,height,image_id,url,width;")
        fields.append("where id = ($platformLogoIds);")

        println(platformLogoIds)

        val platformLogos =  withAuthenticatedHeaders { headers ->
            idgbApi.getPlatformLogos(
                headers, fields.toString())
        }
        println(platformLogos)
        return platforms.filter { isProperPlatform(it) }.map { platform ->
            object : GamePlatformEntity {
                override val id: String
                    get() = platform.id
                override val name: String
                    get() = platform.name
                override val icon: String
                    get() = ("https:" + platformLogos.find { platformLogo ->
                        platformLogo.id == platform.platform_logo
                    }?.url)
                        .replace(
                        "t_thumb",
                        "t_thumb_2x"
                    )
            }
        }
    }

    override suspend fun getGameCompanies(gameId: String): List<GameCompanyEntity> {
        val fields = StringBuilder()
        fields.append("fields *;")
        fields.append("where game = ($gameId);")

        val involvedCompanies = withAuthenticatedHeaders { headers ->
            idgbApi.getInvolvedCompanies(headers, fields.toString())
        }

        println(involvedCompanies)

        val companyIds = involvedCompanies.filter {
            (it.publisher || it.developer) && !it.supporting
        }.joinToString(",") {
            it.company
        }

        val companies = withAuthenticatedHeaders { headers ->
            idgbApi.getCompanies(
                headers,
                StringBuilder()
                    .append("fields *;")
                    .append("where id = ($companyIds);")
                    .toString()
            )
        }

        return companies.map { company ->
            val isDeveloper = involvedCompanies.find {
                it.company == company.id
            }?.developer ?: false
            val isPublisher = involvedCompanies.find {
                it.company == company.id
            }?.publisher ?: false
            object : GameCompanyEntity {
                override val name: String
                    get() = company.name
                override val isDeveloper: Boolean
                    get() = isDeveloper
                override val isPublisher: Boolean
                    get() = isPublisher

            }
        }
    }

    override suspend fun getAllGamePlatforms(): List<GamePlatformEntity> {

        val platforms = withAuthenticatedHeaders { headers ->
            idgbApi.getPlatforms(headers, "fields platform_logo, name, slug; sort generation asc; limit 100;")
        }

        return platforms.filter { isProperPlatform(it) }.map { platform ->
            object : GamePlatformEntity {
                override val id: String
                    get() = platform.id
                override val name: String
                    get() = platform.name
                override val icon: String
                    get() = ""
            }
        }
    }

    override suspend fun getGameGenres(id: String): List<GameGenreEntity> {
        val game = getGame(
            id = id,
            gameMetaQuery = GameMetaQueryModel(
                genres = true,
            )
        ) as GameModel

        val genreIds = game.genres?.joinToString(",").let {
            if (it?.get(it.length - 1) == ',') {
                it.substring(0, it.length - 1)
            } else {
                it
            }
        }
        val genres = withAuthenticatedHeaders { headers ->
            idgbApi.getGenres(headers, "fields name, slug; where id = ($genreIds);")
        }
        return genres.map { genre ->
            object : GameGenreEntity {
                override val id: String
                    get() = genre.id
                override val name: String
                    get() = genre.name

                override val url: String
                    get() = genre.url

            }
        }
    }

    override suspend fun getAllGameGenres(): List<GameGenreEntity> {
        val genres = withAuthenticatedHeaders { headers ->
            idgbApi.getGenres(headers, "fields name, slug; limit 200; sort name asc;")
        }
        return genres.map { genre ->
            object : GameGenreEntity {
                override val id: String
                    get() = genre.id
                override val name: String
                    get() = genre.name

                override val url: String
                    get() = genre.url

            }
        }
    }

    private fun isProperPlatform(platform: PlatformModel) = platform.id != "16"

    override suspend fun getGameSteamId(gameId: String): String {
        val fields = StringBuilder()
        fields.append("fields websites;")
        fields.append("where id = $gameId;")
        val game = withAuthenticatedHeaders { headers ->
            idgbApi.getGames(headers, fields.toString())
        }
        val websiteIds = game.first().websites
        val websites = getGameWebsites(websiteIds ?: emptyList())
        val steamWebsite = websites.find { it.contains("steam") } ?: return ""
        return extractSteamId(steamWebsite)
    }

    private suspend fun getGameWebsites(websiteIds: List<String>): List<String> {
        if (websiteIds.isEmpty()) return emptyList()
        val ids = websiteIds.joinToString(postfix = ",").let {
            it.substring(0, it.length - 1)
        }
        val fields = StringBuilder()
        fields.append("fields url,game;")
        fields.append("where id = ($ids);")
        val websites = withAuthenticatedHeaders { headers ->
            idgbApi.getWebsites(
                headers,
                fields.toString()
            )
        }
        return websites.map { website -> website.url }
    }

    override suspend fun getScreenshots(id: String): List<String> {

        val body = StringBuilder()
            .append("fields image_id,url;")
            .append("where game = $id;")
            //.append("where id = ($screenshotIds);")

        val screenshots = withAuthenticatedHeaders { headers ->
            idgbApi.getScreenshots(headers, body.toString())
        }

        return screenshots.map { screenshot -> screenshot.url ?: "" }.filter { it.isNotEmpty() }.map { screenshot ->
            "https:" + screenshot.replace("t_thumb","t_720p")
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


fun String.dateToUnix(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val localDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val string = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).time.toString()
        return  string.split("00").first() + "00"
    }
    return this
}



fun <T> List<T>.commaSeparated(collapse: (T) -> String): String {
    return joinToString(",") { element ->
        collapse.invoke(element)
    }
//        .let { joinedString ->
////        joinedString.substring(0, joinedString.length - 1)
//    }
}