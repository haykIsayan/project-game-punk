package com.example.project_game_punk.features.common.game_progress

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus

class GameProgressBottomSheetController {

    private var propagate: ((GameEntity, (GameProgressStatus) -> Unit) -> Unit)? = null

    fun onPropagate(propagate: (GameEntity, (GameProgressStatus) -> Unit) -> Unit) {
        this.propagate = propagate
    }

    fun displayBottomSheet(
        game: GameEntity,
        onGameProgressSelected: (GameProgressStatus) -> Unit
    ) {
        propagate?.invoke(game, onGameProgressSelected)
    }
}