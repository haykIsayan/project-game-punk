package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameNewsEntity

class GetLatestGamingNewsInteractor(
    private val getRecentGamesInteractor: GetRecentGamesInteractor
) {

    suspend fun execute(): List<GameNewsEntity> {
        val topRecentGames = getRecentGamesInteractor.execute().subList(0,6)

        return emptyList()
    }
}