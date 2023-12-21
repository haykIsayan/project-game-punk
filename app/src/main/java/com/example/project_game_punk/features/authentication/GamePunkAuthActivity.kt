package com.example.project_game_punk.features.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.project_game_punk.features.game_details.largeRadialGradientBrush
import com.example.project_game_punk.ui.theme.ProjectGamePunkTheme
import com.example.project_game_punk.ui.theme.gamePunkAlt
import com.example.project_game_punk.ui.theme.gamePunkPrimary
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GamePunkAuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectGamePunkTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Box(
                        modifier = Modifier.background(
                            largeRadialGradientBrush(
                                listOf(
                                    gamePunkAlt,
                                    gamePunkPrimary
                                )
                            )
                        )
                    ) {
                        AuthScreen(
                            authViewModel = hiltViewModel(),
                            signInViewModel = hiltViewModel(),
                            signUpViewModel = hiltViewModel()
                        )
                    }
                }
            }
        }
    }
}