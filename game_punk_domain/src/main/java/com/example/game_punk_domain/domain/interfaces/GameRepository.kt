package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.models.GameQueryModel

interface GameRepository {
    suspend fun getGames(gameQuery: GameQueryModel): List<GameEntity>

    suspend fun applyBanners(games: List<GameEntity>): List<GameEntity>

    suspend fun getGamePlatforms(id: String): List<GamePlatformEntity>

    suspend fun getGameGenres(id: String): List<GameGenreEntity>

    suspend fun getScreenshots(id: String): List<String>

    suspend fun getGameImages(id: String): List<String>

    suspend fun getGameSteamId(gameId: String): String

    suspend fun getGame(id: String, gameMetaQuery: GameMetaQueryModel): GameEntity
}