package com.example.project_game_punk.features.discover.trending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun TrendingGamesSection(
    viewModel: TrendingGamesViewModel,
    sheetController: GameProgressBottomSheetController,
) {
    val state = viewModel.getState().observeAsState().value

    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { viewModel.loadState() } },
        loadingState = { TrendingGamesSectionLoadingState() },
    ) { games ->
        Column {
            SectionTitle(title = "Trending Games") {

            }
            ItemCarousel(
                items = games,
                itemDecorator = ItemCarouselDecorators.pillItemDecorator,
            ) { game ->
                GameCarouselItem(
                    game = game,
                    sheetController = sheetController
                ) { game, gameProgress ->
                    viewModel.updateGameProgress(game, gameProgress)
                }
            }
        }
    }
}

@Composable
private fun TrendingGamesSectionLoadingState() {
    Column {
        SectionTitle(
            title = "Trending Games",
            isLoading = true
        )
        LazyRow(content = {
            items(4) {index ->
                val showShimmer = remember { mutableStateOf(true) }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .size(
                            120.dp,
                            160.dp
                        )
                        .padding(
                            start = if (index == 0) 12.dp else 6.dp,
                            end = 6.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value))
                    )
                    Box(modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .padding(
                            start = if (index == 0) 12.dp else 6.dp,
                            end = 6.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)))
                }
            }
        })
    }
}
