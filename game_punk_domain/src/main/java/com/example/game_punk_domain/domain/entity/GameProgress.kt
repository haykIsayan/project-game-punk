package com.example.game_punk_domain.domain.entity

    fun gameProgressStatusItems(
        game: GameEntity?
    ): List<GameProgressStatus> {
        val currentStatus = game?.gameExperience?.gameProgressStatus
        val items = listOf(
            GameProgressStatus.following,
            GameProgressStatus.notFollowing,
            GameProgressStatus.playing,
            GameProgressStatus.stopped,
            GameProgressStatus.finished,
            GameProgressStatus.replaying,
            GameProgressStatus.excited
        )
        if (game == null) return items
        return items.filter {
            it != currentStatus
        }
    }


