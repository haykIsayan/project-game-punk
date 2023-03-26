package com.example.project_game_punk.data.game.rawg.models

import androidx.room.*
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GamePlatformEntity
import com.example.project_game_punk.domain.entity.GameProgress

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
    @Ignore val cover: String? = null,
    @Ignore val follows: Int? = null,
    @Ignore val added: Int = 0,
    @Ignore val rating: Float? = null,
    @Ignore override val metaCriticScore: Int = 0,
    @Ignore override val isAdded: Boolean = false,
    @Ignore override val gamePlatforms: List<GamePlatformEntity>? = null,
    @Ignore val summary: String? = null,
    @ColumnInfo(name = "game_progress_status") var gameProgressStatus: GameProgressStatus? = null,
): GameEntity {

    override val description: String?
        get() = summary

    override val numAdded: Int
        get() = added

    override val banners: List<String>?
        get() = screenshots

    override val gameArtworks: List<String>?
        get() = artworks

    override val backgroundImage: String?
        get() = cover

   override val gameProgress: GameProgress
        get() = when (gameProgressStatus) {
            GameProgressStatus.playing -> GameProgress.PlayingGameProgress
            GameProgressStatus.stopped -> GameProgress.StoppedGameProgress
            GameProgressStatus.finished -> GameProgress.FinishedGameProgress
            GameProgressStatus.replaying -> GameProgress.ReplayingGameProgress
            GameProgressStatus.notFollowing -> GameProgress.NotFollowingGameProgress
            GameProgressStatus.following -> GameProgress.FollowingGameProgress
        else -> GameProgress.NotFollowingGameProgress
    }

    override fun updateGameProgress(gameProgress: GameProgress): GameEntity {
        val status  = when (gameProgress) {
            GameProgress.FollowingGameProgress -> GameProgressStatus.following
            GameProgress.NotFollowingGameProgress -> GameProgressStatus.notFollowing
            GameProgress.FinishedGameProgress -> GameProgressStatus.finished
            GameProgress.PlayingGameProgress -> GameProgressStatus.playing
            GameProgress.ReplayingGameProgress -> GameProgressStatus.replaying
            GameProgress.StoppedGameProgress -> GameProgressStatus.stopped
        }
        return copy(gameProgressStatus = status)
    }

    override fun equals(other: Any?) = (other as? GameModel)?.id == this.id
}

enum class GameProgressStatus {
    following,
    notFollowing,
    playing,
    stopped,
    finished,
    replaying
}