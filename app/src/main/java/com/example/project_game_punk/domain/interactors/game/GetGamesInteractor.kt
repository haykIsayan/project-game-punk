package com.example.project_game_punk.domain.interactors.game

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.domain.interfaces.PunkParamInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameQueryModel

class GetGamesInteractor constructor(
    private val trackedGamesCache: TrackedGamesCache,
    private val gameRepository: GameRepository,
): PunkParamInteractor<List<GameModel>, GameQueryModel> {
    override suspend fun execute(param: GameQueryModel): List<GameModel> {
        val games = gameRepository.getGames(param)
        return trackedGamesCache.applyCache(games)
    }
}