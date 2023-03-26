package com.example.project_game_punk.features.game_details

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.project_game_punk.features.game_details.sections.GameDetailsHeader
import com.example.project_game_punk.features.game_details.sections.GameSynopsisSection
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsSection
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel

@Composable
fun GameDetailsScreen(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {
    when (gameId) {
        null -> NoGameIdState()
        else -> {
            gameDetailsViewModel.loadGame(id = gameId)
            gamePlatformsViewModel.loadState(param = gameId)
            gameScreenshotsViewModel.loadState(param = gameId)
            GameDetailsScreenContent(
                gameDetailsViewModel = gameDetailsViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel
            )
        }
    }
}

@Composable
private fun NoGameIdState() {
    Text(text = "Something went wrong")
}

@Composable
private fun GameDetailsScreenContent(
    gameDetailsViewModel: GameDetailsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {

    LazyColumn {
        item {
            GameDetailsHeader(
                gameDetailsViewModel = gameDetailsViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel
            )
        }

        item {
            GameGenreSection()
        }

        item {
            GamePlatformsSection(gamePlatformsViewModel = gamePlatformsViewModel)
        }

        item {
            GameSynopsisSection(gameDetailsViewModel = gameDetailsViewModel)
        }
    }
}


@Composable
fun GameGenreSection() {

}