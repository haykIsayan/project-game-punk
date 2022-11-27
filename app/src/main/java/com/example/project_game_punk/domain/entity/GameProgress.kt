package com.example.project_game_punk.domain.entity

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.project_game_punk.R

sealed class GameProgress(
    @StringRes
    val displayText: Int,
    @StringRes
    val actionText: Int,
    @ColorRes
    val color: Int,
    @ColorRes
    val textColor: Int
) {
    object FollowingGameProgress: GameProgress(
        R.string.following_game_progress,
        R.string.following_game_progress,
        R.color.white,
        R.color.black
    )

    object NotFollowingGameProgress: GameProgress(
        R.string.not_following_game_progress,
        R.string.unfollow,
        R.color.black,
        R.color.white,
    )

    object PlayingGameProgress: GameProgress(
        R.string.playing_game_progress,
        R.string.playing_game_progress,
        R.color.purple_500,
        R.color.white,
    )

    object StoppedGameProgress: GameProgress(
        R.string.stopped_game_progress,
        R.string.stopped_game_progress,
        R.color.red,
        R.color.white,
    )

    object FinishedGameProgress: GameProgress(
        R.string.finished_game_progress,
        R.string.finished_game_progress,
        R.color.apple,
        R.color.white,
    )

    object ReplayingGameProgress: GameProgress(
        R.string.replaying_game_progress,
        R.string.replaying_game_progress,
        R.color.purple_500,
        R.color.white,
    )

    companion object {
        fun gameProgressItems(
            game: GameEntity?
        ): List<GameProgress> {
            val items = listOf(
                FollowingGameProgress,
                NotFollowingGameProgress,
                PlayingGameProgress,
                StoppedGameProgress,
                FinishedGameProgress,
                ReplayingGameProgress
            )
            if (game == null) return items
            return items.filter {
                it != game.gameProgress
            }
        }
    }
}

