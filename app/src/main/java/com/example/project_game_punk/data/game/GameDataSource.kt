package com.example.project_game_punk.data.game

import android.util.Log
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort

class GameDataSource(private val api: RawgApi): GameRepository {
    override suspend fun getGames(gameQuery: GameQueryModel): List<GameModel> {
        val ordering = when(gameQuery.sort) {
            GameSort.trending -> "-added"
            GameSort.highestRated -> "-rating"
        }
        val dates = if (gameQuery.dateRangeEnd.isNotEmpty() && gameQuery.dateRangeStart.isNotEmpty()) {
            val x = "${gameQuery.dateRangeStart},${gameQuery.dateRangeEnd}"
            Log.d("Haykk", x)
            x
        } else {
            "2021-10-30,2022-10-30"
        }
        val metacritic = if (gameQuery.isHighestRated) "70,100" else ""
//        val ids = "650608,274758,58618"
        val ids = null
        val response = api.getGames(
            gameQuery.query,
            dates = dates,
            ordering = ordering,
            metacritic = metacritic,
            null,
            true,
            excludeAdditions = true,
            searchExact = true,
            ids = ids
        )
        return response.results
    }

    override suspend fun getGame(id: String): GameModel {
        return GameModel(id = "asdf", name = "sgsdfg")
    }
}