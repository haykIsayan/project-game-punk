package com.example.game_punk_domain.domain.interactors

import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetAllAvailableGameGenresInteractor(
    private val gameRepository: GameRepository
) {
    suspend fun execute(): List<GameGenreEntity> {
        return gameRepository.getAllGameGenres()
    }
}