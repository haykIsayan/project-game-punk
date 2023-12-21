package com.example.game_punk_domain.domain.interactors.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor

class GetNewsForNowPlayingGamesInteractor(
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor,
) {

    suspend fun execute(): List<GameNewsEntity> {
        val nowPlayingGames = getNowPlayingGamesInteractor.execute()
        return emptyList()
    }
}