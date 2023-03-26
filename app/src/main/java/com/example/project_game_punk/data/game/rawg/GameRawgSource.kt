package com.example.project_game_punk.data.game.rawg

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.entity.GameMetaQueryModel
import com.example.project_game_punk.domain.entity.GamePlatformEntity
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort

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

    override suspend fun applyBanners(games: List<GameEntity>): List<GameEntity> {
        return games
    }

    override suspend fun getGamePlatforms(id: String): List<GamePlatformEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getScreenshots(id: String): List<String> {
        return emptyList()
    }

    override suspend fun getGameImages(id: String): List<String> {
        return emptyList()
    }

    override suspend fun getGame(id: String, gameMetaQuery: GameMetaQueryModel): GameEntity {
        TODO("Not yet implemented")
    }
}