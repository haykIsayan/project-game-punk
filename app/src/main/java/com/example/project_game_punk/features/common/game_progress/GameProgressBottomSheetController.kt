package com.example.project_game_punk.features.common.game_progress

import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.models.GameModel

class GameProgressBottomSheetController {

    private var propagate: ((GameModel, (GameProgress) -> Unit) -> Unit)? = null


    fun onPropagate(propagate: (GameModel, (GameProgress) -> Unit) -> Unit) {
        this.propagate = propagate
    }

    fun displayBottomSheet(
        game: GameModel,
        onGameProgressSelected: (GameProgress) -> Unit
    ) {
        propagate?.invoke(game, onGameProgressSelected)
    }
}