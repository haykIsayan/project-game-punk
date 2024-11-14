@file:OptIn(ExperimentalMaterialApi::class)

package com.example.project_game_punk.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressModalBottomSheet
import com.example.project_game_punk.features.game_details.largeRadialGradientBrush
import com.example.project_game_punk.ui.theme.ProjectGamePunkTheme
//import com.example.project_game_punk.ui.theme.gamePunkAlt
//import com.example.project_game_punk.ui.theme.gamePunkPrimary
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProjectGamePunkTheme {

                window.statusBarColor = gamePunkPrimaryDark.toArgb()
                window.navigationBarColor = gamePunkPrimaryDark.toArgb()

                Surface(
                    modifier = Modifier.fillMaxSize()

                ) {
                    val navController = rememberNavController()
                    val sheetController = GameProgressBottomSheetController()

                    LaunchedEffect(Unit) {
                        GamePunkNavigator.setOnNavigateRoute { route ->
                            navController.navigate(route = route)
                        }

                        GamePunkNavigator.setOnGoBack {
                            navController.popBackStack()
                        }
                    }

                    Scaffold(
                        bottomBar = {
//                            MainBottomNavigation(navController)
                        },
//                        floatingActionButton = {
//                            FloatingActionButton(
//                                backgroundColor = gamePunkPrimaryLight,
//                                onClick = {
//                                    GamePunkNavigator.navigate("create_post")
//                                }
//                            ) {
//                                Icon(imageVector = Icons.Filled.PostAdd, contentDescription = "")
//                            }
//                        }
                    ) {
                        val padding = it
                        Box(modifier = Modifier
                            .background(
                                largeRadialGradientBrush(
                                    listOf(
//                                        gamePunkAlt,
//                                        gamePunkPrimary
                                        gamePunkPrimaryLight,
                                        gamePunkPrimaryDark,
                                    )
                                )
                            )
//                            .padding(bottom = 60.dp)
                            .fillMaxSize()
                        ) {
                            NavigationComponent(
                                navController,
                                sheetController
                            )
                        }
                    }
                    MainGameProgressBottomSheet(sheetController)
                }
            }
        }
    }

    companion object {
        const val USER_ID_INTENT_EXTRA = "intent_extra_user_id"
    }

}

@Composable
fun MainGameProgressBottomSheet(controller: GameProgressBottomSheetController) {

    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val onGameProgressSelectedState = remember { mutableStateOf<(((GameProgressStatus) -> Unit))?>(null) }
    val gameState = remember { mutableStateOf<GameEntity?>(null)}

    controller.onPropagate { game, onProgressSelected ->
        scope.launch { state.animateTo(ModalBottomSheetValue.Expanded) }
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

object GamePunkNavigator {
    private var onNavigateRoute: ((route: String) -> Unit)? = null
    private var onGoBack: (() -> Unit)? = null

    fun setOnNavigateRoute(callback: (route: String) -> Unit) {
        onNavigateRoute = callback
    }

    fun setOnGoBack(callback: () -> Unit) {
        onGoBack = callback
    }

    fun navigate(route: String) {
        onNavigateRoute?.invoke(route)
    }

    fun goBack() {
        onGoBack?.invoke()
    }
}
