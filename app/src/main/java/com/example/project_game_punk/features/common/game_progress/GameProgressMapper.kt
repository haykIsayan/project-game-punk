package com.example.project_game_punk.features.common.game_progress

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.game_punk_domain.domain.entity.GameProgress
import com.example.project_game_punk.R

object GameProgressMapper {
    @ColorRes
    fun color(gameProgress: GameProgress): Int {
        return when(gameProgress) {
            GameProgress.FinishedGameProgress -> R.color.apple
            GameProgress.FollowingGameProgress -> R.color.white
            GameProgress.NotFollowingGameProgress -> R.color.transparent
            GameProgress.PlayingGameProgress -> R.color.blue
            GameProgress.ReplayingGameProgress -> R.color.blue
            GameProgress.StoppedGameProgress -> R.color.red
            GameProgress.ExcitedGameProgress -> R.color.gold
        }
    }

    @ColorRes
    fun displayTextColor(gameProgress: GameProgress): Int {
        return when(gameProgress) {
            GameProgress.FollowingGameProgress -> R.color.black
            else -> R.color.white
        }
    }

    @StringRes
    fun displayText(gameProgress: GameProgress): Int {
       return when (gameProgress) {
           GameProgress.FinishedGameProgress -> R.string.finished_game_progress
           GameProgress.FollowingGameProgress -> R.string.following_game_progress
           GameProgress.NotFollowingGameProgress -> R.string.not_following_game_progress
           GameProgress.PlayingGameProgress -> R.string.playing_game_progress
           GameProgress.ReplayingGameProgress -> R.string.replaying_game_progress
           GameProgress.StoppedGameProgress -> R.string.stopped_game_progress
           GameProgress.ExcitedGameProgress -> R.string.excited_game_progress
       }
    }

    @StringRes
    fun actionText(gameProgress: GameProgress): Int {
        return when (gameProgress) {
            GameProgress.FinishedGameProgress -> R.string.finished_game_progress
            GameProgress.FollowingGameProgress -> R.string.following_game_progress
            GameProgress.NotFollowingGameProgress -> R.string.unfollow
            GameProgress.PlayingGameProgress -> R.string.playing_game_progress
            GameProgress.ReplayingGameProgress -> R.string.replaying_game_progress
            GameProgress.StoppedGameProgress -> R.string.stopped_game_progress
            GameProgress.ExcitedGameProgress -> R.string.excited_game_progress
            else -> displayText(gameProgress)
        }
    }
}