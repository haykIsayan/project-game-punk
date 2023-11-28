package com.example.game_punk_collection_data.data.game.rawg

import com.example.game_punk_domain.domain.entity.*
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort

class GameRawgSource(private val api: RawgApi): GameRepository {
    override suspend fun getGames(gameQuery: GameQueryModel): List<GameEntity> {
        val ordering = when(gameQuery.sort) {
            GameSort.trending -> "-added"
            GameSort.highestRated -> "-rating"
            GameSort.none -> null
            GameSort.recent -> null
        }
        val dates = if (gameQuery.dateRangeEnd.isNotEmpty() && gameQuery.dateRangeStart.isNotEmpty()) {
            val x = "${gameQuery.dateRangeStart},${gameQuery.dateRangeEnd}"
            x
        } else {
            "2021-10-30,2022-10-30"
        }
        val metacritic = /*if (gameQuery.isHighestRated) "70,100" else*/ ""
//        val ids = "650608,274758,58618"
        val ids = null
        val response = api.getGames(
            gameQuery.query,
            dates = dates,
            ordering = ordering,
            metacritic = metacritic,
            null,
            false,
            excludeAdditions = true,
            searchExact = false,
            ids = ids
        )
        return emptyList()
    }

    override suspend fun getGameAgeRating(gameId: String): GameAgeRatingEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getSimilarGames(gameId: String): List<GameEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDLCs(gameId: String): List<GameEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameStores(gameId: String): List<GameStoreEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun applyBanners(games: List<GameEntity>): List<GameEntity> {
        return games
    }

    override suspend fun getGamePlatforms(id: String): List<GamePlatformEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameCompanies(gameId: String): List<GameCompanyEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGamePlatforms(): List<GamePlatformEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameGenres(id: String): List<GameGenreEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGameGenres(): List<GameGenreEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getScreenshots(id: String): List<String> {
        return emptyList()
    }

    override suspend fun getGameImages(id: String): List<String> {
        return emptyList()
    }

    override suspend fun getGameSteamId(gameId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getGame(id: String, gameMetaQuery: GameMetaQueryModel): GameEntity {
        TODO("Not yet implemented")
    }
}