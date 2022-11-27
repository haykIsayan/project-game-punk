@file:OptIn(ExperimentalMaterialApi::class)

package com.example.project_game_punk.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressModalBottomSheet
import com.example.project_game_punk.ui.theme.ProjectGamePunkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectGamePunkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val sheetController = GameProgressBottomSheetController()
                    Scaffold(
                        backgroundColor = Color.Black,
                        bottomBar = { MainBottomNavigation(navController) }
                    ) {
                        NavigationComponent(navController, sheetController)
                    }
                    MainGameProgressBottomSheet(sheetController)
                }
            }
        }
    }
}

@Composable
private fun MainGameProgressBottomSheet(controller: GameProgressBottomSheetController) {

    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var onGameProgressSelected: (((GameProgress) -> Unit))? = null

    val onGameProgressSelectedState = remember { mutableStateOf<(((GameProgress) -> Unit))?>(null) }
    val gameState = remember { mutableStateOf<GameEntity?>(null)}

    controller.onPropagate { game, onProgressSelected ->
        scope.launch { state.animateTo(ModalBottomSheetValue.Expanded) }
//        onGameProgressSelected = onProgressSelected
        onGameProgressSelectedState.value = onProgressSelected
        gameState.value = game
    }

    gameState.value?.let {
        GameProgressModalBottomSheet(state, scope, it) { gameProgress ->
            onGameProgressSelectedState.value?.invoke(gameProgress)
            onGameProgressSelectedState.value = null
            gameState.value = null
        }
    }
}
