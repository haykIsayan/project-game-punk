package com.example.project_game_punk.features.game_details

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import com.example.project_game_punk.features.game_details.sections.achievements.GameAchievementsSection
import com.example.project_game_punk.features.game_details.sections.achievements.GameAchievementsViewModel
import com.example.project_game_punk.features.game_details.sections.age_rating.GameAgeRatingViewModel
import com.example.project_game_punk.features.game_details.sections.developer_publisher.GameDeveloperPublisherViewModel
import com.example.project_game_punk.features.game_details.sections.discussions.GameDiscussionsSection
import com.example.project_game_punk.features.game_details.sections.discussions.GameFollowingUserReviewsSection
import com.example.project_game_punk.features.game_details.sections.discussions.GameFollowingUserReviewsViewModel
import com.example.project_game_punk.features.game_details.sections.discussions.GameRecentRedditPostsSection
import com.example.project_game_punk.features.game_details.sections.discussions.GameRecentRedditPostsViewModel
import com.example.project_game_punk.features.game_details.sections.dlc.GameDLCSection
import com.example.project_game_punk.features.game_details.sections.dlc.GameDLCsViewModel
import com.example.project_game_punk.features.game_details.sections.experience.GameUserReviewViewModel
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresSection
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresViewModel
import com.example.project_game_punk.features.game_details.sections.genre.GameGenresSection
import com.example.project_game_punk.features.game_details.sections.header.GameCoverWithInfo
import com.example.project_game_punk.features.game_details.sections.header.GameDetailsTitle
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsSection
import com.example.project_game_punk.features.game_details.sections.news.GameDetailsNewsViewModel
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsSection
import com.example.project_game_punk.features.game_details.sections.platforms.GamePlatformsViewModel
import com.example.project_game_punk.features.game_details.sections.release_date.GameReleaseDateViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel
import com.example.project_game_punk.features.game_details.sections.similar_games.GameDetailsSimilarGamesSection
import com.example.project_game_punk.features.game_details.sections.similar_games.GameDetailsSimilarGamesViewModel
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.features.main.MainGameProgressBottomSheet
//import com.example.project_game_punk.ui.theme.gamePunkAlt
//import com.example.project_game_punk.ui.theme.gamePunkPrimary
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

@Composable
fun GameDetailsScreen(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameAgeRatingViewModel: GameAgeRatingViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    gameUserReviewViewModel: GameUserReviewViewModel,
    gameFollowingUserReviewsViewModel: GameFollowingUserReviewsViewModel,
    gameAchievementsViewModel: GameAchievementsViewModel,
    gameRecentRedditPostsViewModel: GameRecentRedditPostsViewModel,
    onBackPressed: () -> Unit
) {
    when (gameId) {
        null -> NoGameIdState()
        else -> {
            LaunchedEffect(Unit) {
                gameDetailsViewModel.loadGame(id = gameId)
//                gameAgeRatingViewModel.loadState(param = gameId)
                gameDeveloperPublisherViewModel.loadState(param = gameId)
                gameReleaseDateViewModel.loadState(param = gameId)
                gameStoresViewModel.loadState(param = gameId)
//                gamePlatformsViewModel.loadState(param = gameId)
//                gameGenresViewModel.loadState(param = gameId)
                gameScreenshotsViewModel.loadState(param = gameId)
                gameDetailsNewsViewModel.loadState(param = gameId)
//                gameDLCsViewModel.loadState(param = gameId)
                gameUserReviewViewModel.loadState(param = gameId)
                gameFollowingUserReviewsViewModel.loadState(param = gameId)
                gameAchievementsViewModel.loadState(param = gameId)
                gameRecentRedditPostsViewModel.loadState(param = gameId)
            }
            GameDetailsScreenContent(
                gameId = gameId,
                gameDetailsViewModel = gameDetailsViewModel,
                gameAgeRatingViewModel = gameAgeRatingViewModel,
                gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                gameReleaseDateViewModel = gameReleaseDateViewModel,
                gameStoresViewModel = gameStoresViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameGenresViewModel = gameGenresViewModel,
                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
                gameScreenshotsViewModel = gameScreenshotsViewModel,
                gameDLCsViewModel = gameDLCsViewModel,
                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel,
                gameUserReviewViewModel = gameUserReviewViewModel,
                gameFollowingUserReviewsViewModel = gameFollowingUserReviewsViewModel,
                gameAchievementsViewModel = gameAchievementsViewModel,
                gameRecentRedditPostsViewModel = gameRecentRedditPostsViewModel,
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
    gameAgeRatingViewModel: GameAgeRatingViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    gameUserReviewViewModel: GameUserReviewViewModel,
    gameFollowingUserReviewsViewModel: GameFollowingUserReviewsViewModel,
    gameAchievementsViewModel: GameAchievementsViewModel,
    gameRecentRedditPostsViewModel: GameRecentRedditPostsViewModel,
    onBackPressed: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        val sheetController = GameProgressBottomSheetController()
        GameDetailsScreenContentItems(
            gameId = gameId,
            gameDetailsViewModel = gameDetailsViewModel,
            gameAgeRatingViewModel = gameAgeRatingViewModel,
            gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
            gameReleaseDateViewModel = gameReleaseDateViewModel,
            gameStoresViewModel = gameStoresViewModel,
            gamePlatformsViewModel = gamePlatformsViewModel,
            gameGenresViewModel = gameGenresViewModel,
            gameDetailsNewsViewModel = gameDetailsNewsViewModel,
            gameScreenshotsViewModel = gameScreenshotsViewModel,
            gameDLCsViewModel = gameDLCsViewModel,
            gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel,
            gameUserReviewViewModel = gameUserReviewViewModel,
            gameFollowingUserReviewsViewModel = gameFollowingUserReviewsViewModel,
            gameAchievementsViewModel = gameAchievementsViewModel,
            gameRecentRedditPostsViewModel = gameRecentRedditPostsViewModel,
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
    gameAgeRatingViewModel: GameAgeRatingViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    gameUserReviewViewModel: GameUserReviewViewModel,
    gameAchievementsViewModel: GameAchievementsViewModel,
    gameFollowingUserReviewsViewModel: GameFollowingUserReviewsViewModel,
    gameRecentRedditPostsViewModel: GameRecentRedditPostsViewModel,
    onBackPressed: () -> Unit,
    sheetController: GameProgressBottomSheetController
) {
    val selectedItemIndex = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val colorOne = remember {
        Animatable(gamePunkPrimaryLight)
    }
    val colorTwo = remember {
        Animatable(gamePunkPrimaryDark)
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
            .fillMaxSize()
    ) {

//        item {
//            Spacer(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .height(60.dp)
//            )
//        }

        item {

            Box {
                GameDetailsHeader(
                    gameDetailsViewModel = gameDetailsViewModel,
                    gameAgeRatingViewModel = gameAgeRatingViewModel,
                    gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                    gameReleaseDateViewModel = gameReleaseDateViewModel,
                    gameScreenshotsViewModel = gameScreenshotsViewModel
                ) {
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    gamePunkPrimaryDark.copy(alpha = 0.05f),
                                    gamePunkPrimaryDark.copy(alpha = 0.1f),
                                    gamePunkPrimaryDark.copy(alpha = 0.2f),
                                    gamePunkPrimaryDark.copy(alpha = 0.3f),
                                    gamePunkPrimaryDark.copy(alpha = 0.4f)
                                ),
                                startY = 0.0f,
                                endY = 100.0f
                            )
                        ),

                    )
                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(
                        10.dp
//                        28.dp
                    )
                    .clip(
                        RoundedCornerShape(
//                            topStart = 28.dp,
//                            topEnd = 28.dp
                                    topStart = 10.dp,
                            topEnd = 10.dp
                        )
                    )
                    .background(colorOne.value)
                )

//                Box(
//                    modifier = Modifier
//                        .padding(
//                            10.dp
////                            28.dp
//                        )
//                        .align(Alignment.BottomStart)
//                        .background(Color.Transparent),
//
//                ) {
//                    GameDetailsTitle(gameDetailsViewModel) {
//
//                    }
//                }

            }
        }

        item {
            GameDetailsTitle(gameDetailsViewModel) {

            }
        }

        item {
            GameDetailsProgressButton(
                gameDetailsViewModel = gameDetailsViewModel,
                controller = sheetController
            )
        }

        item {
            GameDetailsTabBar(
                gameDetailsViewModel = gameDetailsViewModel,
                selectedIndex = selectedItemIndex.value,
                onTabSelected = { index ->
                    selectedItemIndex.value = index
                }
            )
        }


        when (selectedItemIndex.value) {
            0 -> gameDetailsInfoTab(
                gameId = gameId,
                gameDetailsViewModel = gameDetailsViewModel,
                gameStoresViewModel = gameStoresViewModel,
                gamePlatformsViewModel = gamePlatformsViewModel,
                gameGenresViewModel = gameGenresViewModel,
                gameDetailsNewsViewModel = gameDetailsNewsViewModel,
                gameDLCsViewModel = gameDLCsViewModel,
                gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel,
                gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
                gameReleaseDateViewModel = gameReleaseDateViewModel
            )

            1 -> gameDetailsExperienceTab(
                gameDetailsViewModel = gameDetailsViewModel,
                gameUserReviewViewModel = gameUserReviewViewModel,
                gameStoresViewModel = gameStoresViewModel,
                gameAchievementsViewModel = gameAchievementsViewModel
            )
        }
    }
}

@Composable
private fun GameDetailsTabBar(
    gameDetailsViewModel: GameDetailsViewModel,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            GamePunkTab(
                isLoading = true,
                selectedItemIndex = selectedIndex,
                items = mutableListOf(
                    "Info",
                    "Your Experience",
                ),
                onClick = { index ->
                    onTabSelected(index)
                }
            )
        }
    ) {
        GamePunkTab(
            selectedItemIndex = selectedIndex,
            items = mutableListOf(
                "Info",
                "Your Experience",
            ),
            onClick = { index ->
                onTabSelected(index)
            }
        )
    }
}

private fun LazyListScope.gameDetailsInfoTab(
    gameId: String?,
    gameDetailsViewModel: GameDetailsViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gamePlatformsViewModel: GamePlatformsViewModel,
    gameGenresViewModel: GameGenresViewModel,
    gameDetailsNewsViewModel: GameDetailsNewsViewModel,
    gameDLCsViewModel: GameDLCsViewModel,
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel
) {

    item {
        GameCoverWithInfo(
            gameDetailsViewModel = gameDetailsViewModel,
            gameDeveloperPublisherViewModel = gameDeveloperPublisherViewModel,
            gameReleaseDateViewModel = gameReleaseDateViewModel,
            onColorLoaded = {}
        )
    }

    item {
        GamePlatformsSection(
            gameDetailsViewModel = gameDetailsViewModel
        ) {
            gameId?.let {
                gamePlatformsViewModel.loadState(gameId)
            }
        }
    }

    item {
        GameGenresSection(
            gameDetailsViewModel = gameDetailsViewModel,
        ) {
            gameId?.let {
                gameGenresViewModel.loadState(gameId)
            }
        }
    }

    item {
        GameDiscussionsSection()
    }

    item {
        GameSynopsisSection(
            gameDetailsViewModel = gameDetailsViewModel
        )
    }

    item {
        GameStoresSection(
            gameStoresViewModel = gameStoresViewModel
        ) {
            gameId?.let {
                gameStoresViewModel.loadState(gameId)
            }
        }
    }

    item {
        GameDetailsNewsSection(
            gameNewsViewModel = gameDetailsNewsViewModel
        )
    }

    item {
        GameDLCSection(
            gameDetailsViewModel = gameDetailsViewModel,
            gameDLCsViewModel = gameDLCsViewModel
        ) {
            gameId?.let {
                gameDLCsViewModel.loadState(gameId)
            }
        }
    }

    item {
        GameDetailsSimilarGamesSection(
            gameDetailsViewModel = gameDetailsViewModel,
            gameDetailsSimilarGamesViewModel = gameDetailsSimilarGamesViewModel
        ) {
            gameId?.let {
                gameDetailsSimilarGamesViewModel.loadState(gameId)
            }
        }
    }
}

private fun LazyListScope.gameDetailsExperienceTab(
    gameDetailsViewModel: GameDetailsViewModel,
    gameUserReviewViewModel: GameUserReviewViewModel,
    gameStoresViewModel: GameStoresViewModel,
    gameAchievementsViewModel: GameAchievementsViewModel
) {
    item {
        GameExperienceSection(
            gameDetailsViewModel = gameDetailsViewModel,
            gameUserReviewViewModel = gameUserReviewViewModel,
            gameStoresViewModel = gameStoresViewModel,
            gameAchievementsViewModel = gameAchievementsViewModel
        )
    }
    item {
        GameAchievementsSection(
            gameAchievementsViewModel = gameAchievementsViewModel
        )
    }

}

private fun LazyListScope.gameDiscussionsTab(
    gameFollowingUserReviewsViewModel: GameFollowingUserReviewsViewModel,
    gameRecentRedditPostsViewModel: GameRecentRedditPostsViewModel
) {
    item {
        GameFollowingUserReviewsSection(
            gameFollowingUserReviewsViewModel = gameFollowingUserReviewsViewModel
        )
    }

    item {
        GameRecentRedditPostsSection(
            gameRecentRedditPostsViewModel = gameRecentRedditPostsViewModel
        )
    }
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
