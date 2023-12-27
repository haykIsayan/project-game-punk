package com.example.game_punk_domain.domain.interactors.news

import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.game_punk_domain.domain.interactors.game.GetNowPlayingGamesInteractor

class GetNewsForNowPlayingGamesInteractor(
    private val getNewsForGameInteractor: GetNewsForGameInteractor,
    private val getNowPlayingGamesInteractor: GetNowPlayingGamesInteractor,
) {
    suspend fun execute(): List<GameNewsEntity> {
        val nowPlayingGames = getNowPlayingGamesInteractor.execute()
        // todo reduce now playing games to 5
        val topNowPlayingNews = nowPlayingGames.map { game ->
            game.id?.let { gameId ->
                getNewsForGameInteractor.execute(gameId = gameId)
            }?.first()
        }.toList().filterNotNull()
        return topNowPlayingNews
    }
}