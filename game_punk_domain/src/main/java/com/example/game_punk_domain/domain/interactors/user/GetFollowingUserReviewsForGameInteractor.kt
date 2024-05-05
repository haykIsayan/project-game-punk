package com.example.game_punk_domain.domain.interactors.user

import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.interactors.game.GetUserTrackedGamesInteractor
import com.example.game_punk_domain.domain.interfaces.UserRepository

class GetFollowingUserReviewsForGameInteractor(
    private val userRepository: UserRepository
) {
    suspend fun execute(
        userId: String,
        gameId: String
    ): List<GameExperienceEntity> {
        return userRepository.getFollowingUserReviewsForGame(
            userId,
            gameId
        )
    }
}