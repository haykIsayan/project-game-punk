package com.example.project_game_punk.features.common.game_progress

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.R

object GameProgressMapper {
    @ColorRes
    fun color(gameProgressStatus: GameProgressStatus): Int {
        return when(gameProgressStatus) {
            GameProgressStatus.finished -> R.color.apple
            GameProgressStatus.following -> R.color.white
            GameProgressStatus.notFollowing -> R.color.transparent
            GameProgressStatus.playing -> R.color.blue
            GameProgressStatus.replaying -> R.color.blue
            GameProgressStatus.stopped -> R.color.red
            GameProgressStatus.excited -> R.color.gold
        }
    }

    @ColorRes
    fun displayTextColor(gameProgressStatus: GameProgressStatus): Int {
        return when(gameProgressStatus) {
            GameProgressStatus.following -> R.color.black
            else -> R.color.white
        }
    }

    @StringRes
    fun displayText(gameProgressStatus: GameProgressStatus): Int {
       return when (gameProgressStatus) {
           GameProgressStatus.finished -> R.string.finished_game_progress
           GameProgressStatus.following -> R.string.following_game_progress
           GameProgressStatus.notFollowing -> R.string.not_following_game_progress
           GameProgressStatus.playing -> R.string.playing_game_progress
           GameProgressStatus.replaying -> R.string.replaying_game_progress
           GameProgressStatus.stopped -> R.string.stopped_game_progress
           GameProgressStatus.excited -> R.string.excited_game_progress
       }
    }

    @StringRes
    fun actionText(gameProgressStatus: GameProgressStatus): Int {
        return when (gameProgressStatus) {
            GameProgressStatus.finished -> R.string.finished_game_progress
            GameProgressStatus.following -> R.string.following_game_progress
            GameProgressStatus.notFollowing -> R.string.unfollow
            GameProgressStatus.playing -> R.string.playing_game_progress
            GameProgressStatus.replaying -> R.string.replaying_game_progress
            GameProgressStatus.stopped -> R.string.stopped_game_progress
            GameProgressStatus.excited -> R.string.excited_game_progress
            else -> displayText(gameProgressStatus)
        }
    }
}