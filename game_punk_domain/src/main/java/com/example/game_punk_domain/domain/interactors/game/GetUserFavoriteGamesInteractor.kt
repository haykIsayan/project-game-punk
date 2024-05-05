package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameEntity

class GetUserFavoriteGamesInteractor(
    private val getUserTrackedGamesInteractor: GetUserTrackedGamesInteractor
) {
    suspend fun execute(userId: String?): List<GameEntity> {
        return getUserTrackedGamesInteractor.execute(userId).filter {
            it.gameExperience?.favorite == true
        }
    }
}