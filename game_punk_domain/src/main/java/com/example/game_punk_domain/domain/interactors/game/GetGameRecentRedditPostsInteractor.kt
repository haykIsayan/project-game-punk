package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameRedditPostEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameRecentRedditPostsInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(gameId: String): List<GameRedditPostEntity> {
        return gameRepository.getRecentRedditPosts(gameId)
    }
}