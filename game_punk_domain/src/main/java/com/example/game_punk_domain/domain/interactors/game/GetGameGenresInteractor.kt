package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameGenresInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(id: String): List<GameGenreEntity> {
        return gameRepository.getGameGenres(id)
    }
}