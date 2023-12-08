package com.example.game_punk_domain.domain.entity

sealed class GameProgress(

) {
    object FollowingGameProgress: GameProgress(

    )

    object NotFollowingGameProgress: GameProgress(

    )

    object PlayingGameProgress: GameProgress(

    )

    object StoppedGameProgress: GameProgress(

    )

    object FinishedGameProgress: GameProgress(

    )

    object ExcitedGameProgress: GameProgress(

    )

    object ReplayingGameProgress: GameProgress(

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
                ExcitedGameProgress
            )
            if (game == null) return items
            return items.filter {
                it != game.gameProgress
            }
        }
    }
}

