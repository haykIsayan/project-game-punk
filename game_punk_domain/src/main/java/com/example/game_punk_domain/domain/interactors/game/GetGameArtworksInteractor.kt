package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.interfaces.GameRepository

class GetGameArtworksInteractor (
    private val gameRepository: GameRepository
) {

    suspend fun execute(
        id: String
    ): List<String> {
        return gameRepository.getArtworks(id).filter { it.isNotEmpty() }
    }
}