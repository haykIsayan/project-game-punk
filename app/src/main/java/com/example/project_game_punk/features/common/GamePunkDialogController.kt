package com.example.project_game_punk.features.common

import androidx.compose.runtime.Composable

class GamePunkDialogController(
    private val onDisplay: (shouldDisplay: Boolean) -> Unit
) {
    fun displayDialog(
        dialog: @Composable () -> Unit
    ) {
        onDisplay
    }
}