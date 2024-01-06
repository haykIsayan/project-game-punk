package com.example.project_game_punk.features.common.game_punk_grid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.project_game_punk.ui.theme.ProjectGamePunkTheme

class GamePunkGridActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectGamePunkTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val title = intent.getStringExtra(GRID_TITLE_INTENT_EXTRA) ?: ""
                    val gameIds = intent.getStringArrayExtra(GAME_IDS_INTENT_EXTRA)?.toList()
                    GamePunkGridScreen(
                        title = title,
                        gameIds = gameIds ?: emptyList(),
                        gamePunkGridViewModel = hiltViewModel()
                    )
                }
            }
        }
    }

    companion object {
        const val GRID_TITLE_INTENT_EXTRA = "grid_title"
        const val GAME_IDS_INTENT_EXTRA = "game_ids"
    }
}
