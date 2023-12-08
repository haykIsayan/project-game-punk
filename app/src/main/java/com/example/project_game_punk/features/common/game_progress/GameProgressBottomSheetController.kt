package com.example.project_game_punk.features.common.game_progress

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgress

class GameProgressBottomSheetController {

    private var propagate: ((GameEntity, (GameProgress) -> Unit) -> Unit)? = null


    fun onPropagate(propagate: (GameEntity, (GameProgress) -> Unit) -> Unit) {
        this.propagate = propagate
    }

    fun displayBottomSheet(
        game: GameEntity,
        onGameProgressSelected: (GameProgress) -> Unit
    ) {
        propagate?.invoke(game, onGameProgressSelected)
    }
}