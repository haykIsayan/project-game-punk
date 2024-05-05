package com.example.game_punk_collection_data.data.game

import com.example.game_punk_domain.domain.entity.GameAchievementEntity
import com.example.game_punk_domain.domain.entity.GameAgeRatingEntity
import com.example.game_punk_domain.domain.entity.GameCompanyEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.entity.GameRedditPostEntity
import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.game_punk_domain.domain.entity.GameVideoEntity
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameQueryModel

class GameCache: GameRepository {


    companion object {
        val gamesCache = mutableMapOf<GameQueryModel, List<GameEntity>>()
    }

    override suspend fun getGames(gameQuery: GameQueryModel): List<GameEntity> {
        return gamesCache[gameQuery] ?: emptyList()
    }

    fun cacheGames(gameQuery: GameQueryModel, games: List<GameEntity>) {
        gamesCache[gameQuery] = games
    }

    override suspend fun getGameAgeRating(gameId: String): GameAgeRatingEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getSimilarGames(gameId: String): List<GameEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentRedditPosts(gameId: String): List<GameRedditPostEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAchievements(gameId: String): List<GameAchievementEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDLCs(gameId: String): List<GameEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameReleaseDate(gameId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getGameStores(gameId: String): List<GameStoreEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun applyBanners(games: List<GameEntity>): List<GameEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGamePlatforms(id: String): List<GamePlatformEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameCompanies(gameId: String): List<GameCompanyEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGamePlatforms(): List<GamePlatformEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameGenres(id: String): List<GameGenreEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGameGenres(): List<GameGenreEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getScreenshots(id: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtworks(id: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameImages(id: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameSteamId(gameId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getGame(id: String, gameMetaQuery: GameMetaQueryModel): GameEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getVideos(gameId: String): List<GameVideoEntity> {
        TODO("Not yet implemented")
    }
}