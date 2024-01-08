package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.*
import com.example.game_punk_domain.domain.models.GameQueryModel

interface GameRepository {
    suspend fun getGames(gameQuery: GameQueryModel): List<GameEntity>

    suspend fun getGameAgeRating(gameId: String): GameAgeRatingEntity

    suspend fun getSimilarGames(gameId: String): List<GameEntity>

    suspend fun getGameDLCs(gameId: String): List<GameEntity>

    suspend fun getGameReleaseDate(gameId: String): String

    suspend fun getGameStores(gameId: String): List<GameStoreEntity>

    suspend fun applyBanners(games: List<GameEntity>): List<GameEntity>

    suspend fun getGamePlatforms(id: String): List<GamePlatformEntity>

    suspend fun getGameCompanies(gameId: String): List<GameCompanyEntity>

    suspend fun getAllGamePlatforms(): List<GamePlatformEntity>

    suspend fun getGameGenres(id: String): List<GameGenreEntity>

    suspend fun getAllGameGenres(): List<GameGenreEntity>

    suspend fun getScreenshots(id: String): List<String>

    suspend fun getArtworks(id: String): List<String>

    suspend fun getGameImages(id: String): List<String>

    suspend fun getGameSteamId(gameId: String): String

    suspend fun getGame(id: String, gameMetaQuery: GameMetaQueryModel): GameEntity

    suspend fun getVideos(gameId: String): List<GameVideoEntity>
}