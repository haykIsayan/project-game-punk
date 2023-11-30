package com.example.project_game_punk.features.game_details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.project_game_punk.ui.theme.ProjectGamePunkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectGamePunkTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    androidx.compose.material.Scaffold(
                        backgroundColor = Color.Black
                    ) {
                        GameDetailsScreen(
                            gameId = intent.getStringExtra(GAME_ID_INTENT_EXTRA),
                            gameDetailsViewModel = hiltViewModel(),
                            gameDeveloperPublisherViewModel = hiltViewModel(),
                            gameReleaseDateViewModel = hiltViewModel(),
                            gameStoresViewModel = hiltViewModel(),
                            gamePlatformsViewModel = hiltViewModel(),
                            gameGenresViewModel = hiltViewModel(),
                            gameScreenshotsViewModel = hiltViewModel(),
                            gameDetailsNewsViewModel = hiltViewModel(),
                            gameDLCsViewModel = hiltViewModel(),
                            gameDetailsSimilarGamesViewModel = hiltViewModel()
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val GAME_ID_INTENT_EXTRA = "game_id"
    }
}
