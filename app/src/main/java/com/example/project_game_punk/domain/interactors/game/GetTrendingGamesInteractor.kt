package com.example.project_game_punk.domain.interactors.game

import android.util.Log
import com.example.project_game_punk.domain.interfaces.PunkInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameSort

class GetTrendingGamesInteractor constructor(
    private val getGamesInteractor: GetGamesInteractor
): PunkInteractor<List<GameModel>> {
    override suspend fun execute(): List<GameModel> {
        val gameQuery = GetGameQueryWithRecentDatesInteractor().execute()

        val games = getGamesInteractor.execute(gameQuery.copy(sort = GameSort.trending, isHighestRated = false))



        games.forEach {
            Log.d("Haykk", it.id.toString())
        }


        return games.filter {
            it.numAdded >= 1000
        }
    }
}