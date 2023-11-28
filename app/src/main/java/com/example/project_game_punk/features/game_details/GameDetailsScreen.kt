package com.example.project_game_punk.features.game_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.sections.header.GameDetailsHeader
import com.example.project_game_punk.features.game_details.sections.GameSynopsisSection
import com.example.project_game_punk.features.game_details.sections.developer_publisher.GameDeveloperPublisherViewModel
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresSection
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresSection
import com.example.project_game_punk.features.game_details.sections.header.GameDetailsTitle
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsSection
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsViewModel
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsSection
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel
import com.example.project_game_punk.features.game_details.sections.similar_games.GameDetailsSimilarGamesSection
import com.example.project_game_punk.features.game_details.sections.similar_games.GameDetailsSimilarGamesViewModel
import com.example.project_game_punk.features.main.MainGameProgressBottomSheet

@Composable
fun GameDetailsScreen(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel
) {
    when (gameId) {
        null -> NoGameIdState()
        else -> {
            gameDetailsViewModel.loadGame(id = gameId)
            gameDeveloperPublisherViewModel.loadState(param = gameId)
            gameStoresViewModel.loadState(param = gameId)
            gamePlatformsViewModel.loadState(param = gameId)
            gameGenresViewModel.loadState(param = gameId)
            gameScreenshotsViewModel.loadState(param = gameId)
            gameDetailsNewsViewModel.loadState(param = gameId)
            gameDetailsSimilarGamesViewModel.loadState(param = gameId)
            GameDetailsScreenContent(
                gameDetailsViewModel = gameDetailsViewModel,
                gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                gameStoresViewModel = gameStoresViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameGenresViewModel = gameGenresViewModel,
                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel,
                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
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
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        GameDetailsScreenContentItems(
            gameDetailsViewModel = gameDetailsViewModel,
            gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
            gameStoresViewModel = gameStoresViewModel,
            gamePlatformsViewModel = gamePlatformsViewModel,
            gameGenresViewModel = gameGenresViewModel,
            gameDetailsNewsViewModel = gameDetailsNewsViewModel,
            gameScreenshotsViewModel = gameScreenshotsViewModel,
            gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
        )
        val sheetController = GameProgressBottomSheetController()
        GameDetailsProgressButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            gameDetailsViewModel = gameDetailsViewModel,
            controller = sheetController
        )
        MainGameProgressBottomSheet(sheetController)
    }
}

@Composable
private fun GameDetailsScreenContentItems(
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel
) {
    val backgroundColor = remember {
        mutableStateOf(0)
    }
    LazyColumn(
        modifier = Modifier
            .background(
                if (backgroundColor.value != 0) {
                    largeRadialGradientBrush(
                        listOf(
                            Color(backgroundColor.value).copy(alpha = 0.7f),
                            Color(backgroundColor.value).copy(alpha = 0.4f)
                        )
                    )

                } else {
                    largeRadialGradientBrush(
                        listOf(
                            Color.DarkGray.copy(alpha = 0.5f),
                            Color.DarkGray.copy(alpha = 0.2f),
                        )
                    )
                }
            )
    ) {

        item {
            GameDetailsTitle(gameDetailsViewModel = gameDetailsViewModel)
        }

        item {
            GameDetailsHeader(
                gameDetailsViewModel = gameDetailsViewModel,
                gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel
            ) {
                backgroundColor.value = it
            }
        }

        item {
            GameSynopsisSection(
                gameDetailsViewModel = gameDetailsViewModel
            )
        }

        item {
            GameStoresSection(
                gameStoresViewModel = gameStoresViewModel
            )
        }

        item {
            GamePlatformsSection(
                gamePlatformsViewModel = gamePlatformsViewModel
            )
        }

        item {
            GameGenresSection(
                gameGenresViewModel = gameGenresViewModel
            )
        }

        item {
            GameDetailsNewsSection(
                gameNewsViewModel = gameDetailsNewsViewModel
            )
        }

        item {
            GameDetailsSimilarGamesSection(
                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
            )
        }

        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp))
        }
    }
}

@Composable
private fun GameDetailsProgressButton(
    modifier: Modifier,
    color: Color = Color.Transparent,
    gameDetailsViewModel: GameDetailsViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = gameDetailsViewModel.getState().observeAsState()
    LoadableStateWrapper(state = state.value) { game ->
        game?.let {
            Column(
                modifier = modifier
            ) {
                Box(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    color,
                                    Color.Black.copy(alpha = 0.9f)
                                ),
                            )
                        )
                )
                GameProgressButton(
                    game = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    controller = controller,
                    onProgressSelected = gameDetailsViewModel::updateGameProgress
                )
            }
        }
    }
}


fun largeRadialGradientBrush(
    colors: List<Color>
) = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = colors,
            center = size.center,
            radius = biggerDimension / 2f,
            colorStops = listOf(0f, 0.95f)
        )
    }
}
