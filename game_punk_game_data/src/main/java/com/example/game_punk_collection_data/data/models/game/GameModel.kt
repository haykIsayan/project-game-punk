package com.example.game_punk_collection_data.data.models.game

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.game_punk_domain.domain.entity.*

@Entity
data class GameModel(
    @PrimaryKey(autoGenerate = true) var uuid: Long = 0,
    @Ignore override val id: String? = null,
    @Ignore val slug: String? = null,
    @Ignore override val name: String? = null,
    @Ignore val background_image: String? = null,
    @Ignore override val banner: String? = null,
    @Ignore val screenshots: List<String>? = null,
    @Ignore val artworks: List<String>? = null,
    @Ignore val platforms: List<String>? = null,
    @Ignore val genres: List<String>? = null,
    @Ignore val cover: String? = null,
    @Ignore val follows: Int? = null,
    @Ignore val added: Int = 0,
    @Ignore val aggregated_rating: Float? = null,
    @Ignore override val gamePlatforms: List<GamePlatformEntity>? = null,
    @Ignore override val gameGenres: List<GameGenreEntity>? = null,
    @Ignore val summary: String? = null,
    @Ignore val websites: List<String>? = null,
    @Ignore val age_ratings: List<String>? = null,
    @Ignore val similar_games: List<String>? = null,
    @Ignore val dlcs: List<String>? = null,
    @Ignore override val steamId: String? = null,
    @Ignore override val keywords: List<String>? = null,
    @Ignore override val videos: List<GameVideoEntity>? = null,
    @Embedded var gameExperienceModel: GameExperienceModel? = GameExperienceModel(
        0,
        favorite = false,
        gameProgressStatus = GameProgressStatus.notFollowing
    )
): GameEntity {

    override val description: String?
        get() = summary

    override val numAdded: Int
        get() = added

    override val score: Int
        get() = aggregated_rating?.toInt() ?: 0

    override val gameExperience: GameExperienceEntity?
        get() = gameExperienceModel

    override fun updateGameExperience(
        gameExperience: GameExperienceEntity
    ): GameEntity {
        return copy(
            gameExperienceModel = GameExperienceModel(
                0,
                gameExperience.userScore,
                gameExperience.favorite,
                gameExperience.storeId,
                gameExperience.platformId,
                gameExperience.userReview,
                gameExperience.gameProgressStatus,
                gameExperience.gameId,
                gameExperience.userId
            )
        )
    }

    override fun toggleIsFavorite(): GameEntity {
        val isFavorite = gameExperienceModel?.favorite ?: false
        val experience = gameExperienceModel?.copy(favorite = !isFavorite) ?: return this
        return updateGameExperience(experience)
    }

    override fun updateUserScore(userScore: Float): GameEntity {
        return gameExperienceModel?.copy(userScore = userScore)?.let { updatedGameExperience ->
            updateGameExperience(updatedGameExperience)
        } ?: this
    }


    override fun updateGameProgressStatus(gameProgressStatus: GameProgressStatus): GameEntity {
        return gameExperienceModel?.copy(gameProgressStatus = gameProgressStatus)?.let { updatedGameExperience ->
            updateGameExperience(updatedGameExperience)
        } ?: this
    }

    override fun updateGameExperiencePlatform(platformId: String): GameEntity {
        return gameExperienceModel?.copy(platformId = platformId)?.let { updatedGameExperience ->
            updateGameExperience(updatedGameExperience)
        } ?: this
    }

    override fun updateGameExperienceStore(storeId: String): GameEntity {
        return gameExperienceModel?.copy(storeId = storeId)?.let { updatedGameExperience ->
            updateGameExperience(updatedGameExperience)
        } ?: this
    }

    override val banners: List<String>?
        get() = screenshots

    override val gameArtworks: List<String>?
        get() = artworks

    override val backgroundImage: String?
        get() = cover


    override fun updateVideos(videos: List<GameVideoEntity>): GameEntity {
        return copy(videos = videos)
    }

    override fun equals(other: Any?) = (other as? GameModel)?.id == this.id
}

@Entity
data class GameExperienceModel(
    @PrimaryKey(autoGenerate = true) var experienceUuid: Long = 0,
    @ColumnInfo(name = "user_score") override var userScore: Float = 0f,
    @ColumnInfo(name = "is_favorite") override var favorite: Boolean? = null,
    @ColumnInfo(name = "store_id") override val storeId: String? = null,
    @ColumnInfo(name = "platform_id") override val platformId: String? = null,
    @ColumnInfo(name = "user_review") override val userReview: String? = null,
    @ColumnInfo(name = "game_progress_status") override var gameProgressStatus: GameProgressStatus? = null,
    @ColumnInfo(name = "user_id") override var userId: String = "",
    @ColumnInfo(name = "game_id") override var gameId: String = ""
): GameExperienceEntity {

    override fun updateGameProgressStatus(
        gameProgressStatus: GameProgressStatus
    ): GameExperienceEntity {
        return copy(gameProgressStatus = gameProgressStatus)
    }

    override fun updateIsFavorite(
        isFavorite: Boolean
    ): GameExperienceEntity {
        return copy(favorite = isFavorite)
    }

    override fun updatePlatformId(
        platformId: String
    ): GameExperienceEntity {
        return copy(platformId = platformId)
    }

    override fun updateStoreId(
        storeId: String
    ): GameExperienceEntity {
        return copy(storeId = storeId)
    }

}