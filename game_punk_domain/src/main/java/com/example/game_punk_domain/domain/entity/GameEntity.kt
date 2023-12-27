package com.example.game_punk_domain.domain.entity

interface GameEntity {
    val id: String?
    val name: String?
    val description: String?
    val backgroundImage: String?
    val banner: String?
    val banners: List<String>?
    val gameArtworks: List<String>?
    val numAdded: Int
    val score: Int
    val gameExperience: GameExperienceEntity?
    val gamePlatforms: List<GamePlatformEntity>?
    val gameGenres: List<GameGenreEntity>?
    val keywords: List<String>?
    val steamId: String?
    val videos: List<GameVideoEntity>?

    fun toggleIsFavorite(): GameEntity

    fun updateUserScore(userScore: Float): GameEntity

    fun updateGameExperience(gameExperience: GameExperienceEntity): GameEntity

    fun updateGameProgressStatus(gameProgressStatus: GameProgressStatus): GameEntity

    fun updateGameExperiencePlatform(platformId: String): GameEntity

    fun updateGameExperienceStore(storeId: String): GameEntity

    fun updateVideos(videos: List<GameVideoEntity>): GameEntity
}
