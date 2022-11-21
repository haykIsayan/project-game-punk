package com.example.project_game_punk.features.common.game_progress

import androidx.compose.runtime.Composable
import com.example.project_game_punk.domain.entity.GameProgress

class GameProgressBottomSheetController {
    private var onDisplayRequested: (() -> Unit)? = null
    private val onGameProgressSelectedListeners = mutableListOf<((GameProgress) -> Unit)>()
    private var onGameProgressSelected: (() -> GameProgress)? = null

    private var propagate: (((GameProgress) -> Unit) -> Unit)? = null

//
//    fun setOnDisplayRequested(onDisplayRequested: () -> Unit) {
//        this.onDisplayRequested = onDisplayRequested
//    }


    fun onGameProgressSelected(onGameProgressSelected: () -> GameProgress) {
//        this.onGameProgressSelected = onGameProgressSelected
    }


    fun onPropagate(propagate: ((GameProgress) -> Unit) -> Unit) {
        this.propagate = propagate
    }

    fun showBottomSheet(): GameProgress? {
        return onGameProgressSelected?.invoke()
    }

    fun displayBottomSheet(
        onGameProgressSelected: (GameProgress) -> Unit
    ) {
//        onGameProgressSelectedListeners.add(onGameProgressSelected)
        propagate?.invoke(onGameProgressSelected)
    }
//
}