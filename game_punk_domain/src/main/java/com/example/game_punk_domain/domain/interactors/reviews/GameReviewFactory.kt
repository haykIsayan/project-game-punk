package com.example.game_punk_domain.domain.interactors.reviews

import com.example.game_punk_domain.domain.entity.GameReviewEntity

interface GameReviewFactory {

    fun create(
        userId: String,
        gameId: String
    ): GameReviewEntity

}