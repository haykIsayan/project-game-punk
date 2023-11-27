package com.example.project_game_punk.features.discover.trending

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun TrendingGamesSection(
    viewModel: TrendingGamesViewModel,
    sheetController: GameProgressBottomSheetController,
) {
    val state = viewModel.getState().observeAsState().value
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionTitle(title = "Trending Games")
            IconButton(modifier = Modifier.size(50.dp, 50.dp),
                onClick = {

                }) {
                Icon(Icons.Filled.KeyboardArrowRight, "", tint = Color.White)
            }
        }
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage -> DiscoverGameFailState(errorMessage) { viewModel.loadState() } },
            loadingState = { TrendingGamesSectionLoadingState() },
        ) { games ->
            ItemCarousel(items = games) { game ->
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
    LazyRow(content = {
        items(4) {
            val showShimmer = remember { mutableStateOf(true) }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .size(
                        150.dp,
                        200.dp
                    )
                    .padding(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(shimmerBrush(showShimmer = showShimmer.value))
                )
                Box(modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(shimmerBrush(showShimmer = showShimmer.value)))
            }
        }
    })
}
