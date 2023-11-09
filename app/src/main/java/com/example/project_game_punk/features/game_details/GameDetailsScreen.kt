package com.example.project_game_punk.features.game_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.sections.GameDetailsHeader
import com.example.project_game_punk.features.game_details.sections.GameSynopsisSection
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresSection
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsSection
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsViewModel
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsSection
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel
import com.example.project_game_punk.features.main.MainGameProgressBottomSheet

@Composable
fun GameDetailsScreen(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {
    when (gameId) {
        null -> NoGameIdState()
        else -> {
            gameDetailsViewModel.loadGame(id = gameId)
            gamePlatformsViewModel.loadState(param = gameId)
            gameGenresViewModel.loadState(param = gameId)
            gameScreenshotsViewModel.loadState(param = gameId)
            gameDetailsNewsViewModel.loadState(param = gameId)
            GameDetailsScreenContent(
                gameDetailsViewModel = gameDetailsViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameGenresViewModel = gameGenresViewModel,
                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
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
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {

    Box(modifier = Modifier.fillMaxSize()) {
        GameDetailsScreenContentItems(
            gameDetailsViewModel = gameDetailsViewModel,
            gamePlatformsViewModel = gamePlatformsViewModel,
            gameGenresViewModel = gameGenresViewModel,
            gameDetailsNewsViewModel = gameDetailsNewsViewModel,
            gameScreenshotsViewModel = gameScreenshotsViewModel
        )
        val sheetController = GameProgressBottomSheetController()
        GameDetailsProgressButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(12.dp)
                .align(Alignment.BottomCenter),
            gameDetailsViewModel = gameDetailsViewModel,
            controller = sheetController
        )
        MainGameProgressBottomSheet(sheetController)
    }
}

@Composable
private fun GameDetailsScreenContentItems(
    gameDetailsViewModel: GameDetailsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
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
            GameSynopsisSection(gameDetailsViewModel = gameDetailsViewModel)
        }

        item {
            GamePlatformsSection(gamePlatformsViewModel = gamePlatformsViewModel)
        }

        item {
            GameGenresSection(gameGenresViewModel = gameGenresViewModel)
        }

        item {
            GameDetailsNewsSection(gameNewsViewModel = gameDetailsNewsViewModel)
        }


        item {
            Box(modifier = Modifier.fillMaxWidth().height(80.dp))
        }
    }
}

@Composable
private fun GameDetailsProgressButton(
    modifier: Modifier,
    gameDetailsViewModel: GameDetailsViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = gameDetailsViewModel.getState().observeAsState()
    LoadableStateWrapper(state = state.value) { game ->
        game?.let {
            GameProgressButton(
                game = it,
                modifier = modifier,
                controller = controller,
                onProgressSelected = gameDetailsViewModel::updateGameProgress
            )
        }
    }
}
