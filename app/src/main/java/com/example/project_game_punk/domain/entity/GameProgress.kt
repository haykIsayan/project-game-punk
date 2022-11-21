package com.example.project_game_punk.domain.entity

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.room.Entity
import com.example.project_game_punk.R

sealed class GameProgress(
    @StringRes
    val text: Int,
    @ColorRes
    val color: Int
) {
    object PlayingGameProgress: GameProgress(R.string.playing_game_progress, R.color.purple_500)
    object StoppedGameProgress: GameProgress(R.string.stopped_game_progress, R.color.red)
    object FinishedGameProgress: GameProgress(R.string.finished_game_progress, R.color.apple)
    object ReplayingGameProgress: GameProgress(R.string.replaying_game_progress, R.color.purple_500)

    companion object {
        fun gameProgressItems() = listOf(
            PlayingGameProgress,
            StoppedGameProgress,
            FinishedGameProgress,
            ReplayingGameProgress
        )
    }
}

