package com.example.project_game_punk.features.game_details

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.sections.GamePunkTab
import com.example.project_game_punk.features.game_details.sections.experience.GameExperienceSection
import com.example.project_game_punk.features.game_details.sections.header.GameDetailsHeader
import com.example.project_game_punk.features.game_details.sections.GameSynopsisSection
import com.example.project_game_punk.features.game_details.sections.developer_publisher.GameDeveloperPublisherViewModel
import com.example.project_game_punk.features.game_details.sections.dlc.GameDLCSection
import com.example.project_game_punk.features.game_details.sections.dlc.GameDLCsViewModel
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresSection
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresSection
import com.example.project_game_punk.features.game_details.sections.header.GameDetailsTitle
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsSection
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsViewModel
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsSection
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsViewModel
import com.example.project_game_punk.features.game_details.sections.release_date.GameReleaseDateViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel
import com.example.project_game_punk.features.game_details.sections.similar_games.GameDetailsSimilarGamesSection
import com.example.project_game_punk.features.game_details.sections.similar_games.GameDetailsSimilarGamesViewModel
import com.example.project_game_punk.features.main.MainGameProgressBottomSheet
import com.example.project_game_punk.ui.theme.gamePunkAlt
import com.example.project_game_punk.ui.theme.gamePunkPrimary
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

@Composable
fun GameDetailsScreen(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    onBackPressed: () -> Unit
) {
    when (gameId) {
        null -> NoGameIdState()
        else -> {
            gameDetailsViewModel.loadGame(id = gameId)
            gameDeveloperPublisherViewModel.loadState(param = gameId)
            gameReleaseDateViewModel.loadState(param = gameId)
            gameStoresViewModel.loadState(param = gameId)
            gamePlatformsViewModel.loadState(param = gameId)
            gameGenresViewModel.loadState(param = gameId)
            gameScreenshotsViewModel.loadState(param = gameId)
            gameDetailsNewsViewModel.loadState(param = gameId)
            gameDLCsViewModel.loadState(param = gameId)
            gameDetailsSimilarGamesViewModel.loadState(param = gameId)
            GameDetailsScreenContent(
                gameId = gameId,
                gameDetailsViewModel = gameDetailsViewModel,
                gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                gameReleaseDateViewModel = gameReleaseDateViewModel,
                gameStoresViewModel = gameStoresViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameGenresViewModel = gameGenresViewModel,
                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel,
                gameDLCsViewModel = gameDLCsViewModel,
                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel,
                onBackPressed = onBackPressed
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
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    onBackPressed: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        val sheetController = GameProgressBottomSheetController()
        GameDetailsScreenContentItems(
            gameId = gameId,
            gameDetailsViewModel = gameDetailsViewModel,
            gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
            gameReleaseDateViewModel = gameReleaseDateViewModel,
            gameStoresViewModel = gameStoresViewModel,
            gamePlatformsViewModel = gamePlatformsViewModel,
            gameGenresViewModel = gameGenresViewModel,
            gameDetailsNewsViewModel = gameDetailsNewsViewModel,
            gameScreenshotsViewModel = gameScreenshotsViewModel,
            gameDLCsViewModel = gameDLCsViewModel,
            gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel,
            onBackPressed = onBackPressed,
            sheetController = sheetController
        )
        MainGameProgressBottomSheet(sheetController)
    }
}

@Composable
private fun GameDetailsScreenContentItems(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    onBackPressed: () -> Unit,
    sheetController: GameProgressBottomSheetController
) {
    val scope = rememberCoroutineScope()
    val colorOne = remember {
        Animatable(gamePunkAlt)
    }
    val colorTwo = remember {
        Animatable(gamePunkPrimary)
    }
    LazyColumn(
        modifier = Modifier
            .background(
                largeRadialGradientBrush(
                    listOf(
                        colorOne.value,
                        colorTwo.value
                    )
                )
            )
    ) {

        item {
            GameDetailsTitle(
                gameDetailsViewModel = gameDetailsViewModel,
                onBackPressed = onBackPressed
            )
        }

        item {
            GameDetailsHeader(
                gameDetailsViewModel = gameDetailsViewModel,
                gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                gameReleaseDateViewModel = gameReleaseDateViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel
            ) {
                scope.launch {
                    listOf(async {
                        colorOne.animateTo(
                            Color(it).copy(alpha = 0.7f),
                            animationSpec = tween(500)
                        )
                    },
                        async { colorTwo.animateTo(
                            Color(it).copy(alpha = 0.4f),
                            animationSpec = tween(500)
                        ) }
                    ).awaitAll()
                }
            }
        }

        item {
            GameDetailsProgressButton(
                gameDetailsViewModel = gameDetailsViewModel,
                controller = sheetController
            )
        }

        item {
            GameDetailsTabContent(
                gameId = gameId,
                gameDetailsViewModel = gameDetailsViewModel,
                gameStoresViewModel = gameStoresViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameGenresViewModel = gameGenresViewModel,
                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
                gameDLCsViewModel = gameDLCsViewModel,
                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
            )
        }
    }
}

@Composable
private fun GameDetailsTabContent(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel
) {
    val selectedItemIndex = remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .fillMaxWidth()

    ) {
        val state = gameDetailsViewModel.getState().observeAsState().value
        LoadableStateWrapper(
            state = state,
            loadingState = {
                GamePunkTab(
                    isLoading = true,
                    modifier = Modifier.align(Alignment.CenterStart),
                    selectedItemIndex = selectedItemIndex.value,
                    items = mutableListOf(
                        "Info",
                        "Experience"
                    ),
                    onClick = { index ->
                        selectedItemIndex.value = index
                    }
                )
            }
        ) {
            GamePunkTab(
                modifier = Modifier.align(Alignment.CenterStart),
                selectedItemIndex = selectedItemIndex.value,
                items = mutableListOf(
                    "Info",
                    "Experience"
                ),
                onClick = { index ->
                    selectedItemIndex.value = index
                }
            )
        }
    }

    when (selectedItemIndex.value) {
        0 -> GameDetailsInfoTab(
            gameId = gameId,
            gameDetailsViewModel = gameDetailsViewModel,
            gameStoresViewModel = gameStoresViewModel,
            gamePlatformsViewModel = gamePlatformsViewModel,
            gameGenresViewModel = gameGenresViewModel,
            gameDetailsNewsViewModel = gameDetailsNewsViewModel,
            gameDLCsViewModel = gameDLCsViewModel,
            gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
        )

        1 -> GameDetailsExperienceTab(
            gameDetailsViewModel = gameDetailsViewModel,
            gameStoresViewModel = gameStoresViewModel
        )

//    HorizontalPager(
//        state = pagerState,
////        flingBehavior = FlingBehavior,
//        count = 2,
//    ) {index ->
//        when (index) {
//            0 -> GameDetailsInfoTab(
//                gameId = gameId,
//                gameDetailsViewModel = gameDetailsViewModel,
//                gameStoresViewModel = gameStoresViewModel,
//                gamePlatformsViewModel = gamePlatformsViewModel,
//                gameGenresViewModel = gameGenresViewModel,
//                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
//                gameDLCsViewModel = gameDLCsViewModel,
//                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
//            )
//            1 -> GameDetailsExperienceTab(
//                gameDetailsViewModel = gameDetailsViewModel,
//                gameStoresViewModel = gameStoresViewModel
//            )
//        }
//    }
    }
}


@Composable
private fun GameDetailsInfoTab(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel
) {
    Column {
        GameSynopsisSection(
            gameDetailsViewModel = gameDetailsViewModel
        )

        GamePlatformsSection(
            gameDetailsViewModel = gameDetailsViewModel
        ) {
            gameId?.let {
                gamePlatformsViewModel.loadState(gameId)
            }
        }

        GameGenresSection(
            gameDetailsViewModel = gameDetailsViewModel,
        ) {
            gameId?.let {
                gameGenresViewModel.loadState(gameId)
            }
        }

        GameStoresSection(
            gameStoresViewModel = gameStoresViewModel
        ) {
            gameId?.let {
                gameStoresViewModel.loadState(gameId)
            }
        }

        GameDetailsNewsSection(
            gameNewsViewModel = gameDetailsNewsViewModel
        )

        GameDLCSection(
            gameDLCsViewModel = gameDLCsViewModel
        ) {
            gameId?.let {
                gameDLCsViewModel.loadState(gameId)
            }
        }

        GameDetailsSimilarGamesSection(
            gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
        ) {
            gameId?.let {
                gameDetailsSimilarGamesViewModel.loadState(gameId)
            }
        }
    }
}

@Composable
private fun GameDetailsExperienceTab(
    gameDetailsViewModel: GameDetailsViewModel,
    gameStoresViewModel: GameStoresViewModel
) {
    GameExperienceSection(
        gameDetailsViewModel = gameDetailsViewModel,
        gameStoresViewModel = gameStoresViewModel,
    )
}

@Composable
private fun GameDetailsProgressButton(
    gameDetailsViewModel: GameDetailsViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = gameDetailsViewModel.getState().observeAsState()
    LoadableStateWrapper(
        state = state.value,
        loadingState = {
            val showShimmer = remember { mutableStateOf(true) }
            Box(modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(shimmerBrush(showShimmer = showShimmer.value))
                .height(45.dp)
            )
        }
    ) { game ->
        game?.let {
            GameProgressButton(
                game = it,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(45.dp),
                controller = controller,
                onProgressSelected = gameDetailsViewModel::updateGameProgress
            )
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
