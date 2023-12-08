package com.example.project_game_punk.features.game_details.sections.similar_games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.shimmerBrush

@Composable
fun GameDetailsSimilarGamesSection(
    gameDetailsSimilarGamesViewModel: GameDetailsSimilarGamesViewModel,
    reload: () -> Unit = {}
) {
    val state = gameDetailsSimilarGamesViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = {
            GameDetailsSimilarGamesSectionFailedState {
                reload()
            }
        },
        loadingState = { GameDetailsSimilarGamesSectionLoadingState() }
    ) { similarGames ->
        GameDetailsSimilarGamesSectionLoadedState(similarGames)
    }
}



@Composable
private fun GameDetailsSimilarGamesSectionFailedState(reload: () -> Unit) {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(title = "Similar Games")
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .fillMaxWidth()
            .height(160.dp)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp)
                    .clickable {
                        reload()
                    },
                imageVector = Icons.Filled.Sync,
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun GameDetailsSimilarGamesSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(title = "Similar Games", isLoading = true)
        LazyRow(content = {
            items(4) {
                Box(modifier = Modifier
                    .size(
                        120.dp,
                        160.dp
                    )
                    .padding(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(shimmerBrush(showShimmer = showShimmer.value))
                )
            }
        })
    }
}

@Composable
private fun GameDetailsSimilarGamesSectionLoadedState(
    similarGames: List<GameEntity>
) {
    Column {
        SectionTitle(title = "Similar Games")
        ItemCarousel(items = similarGames) { game ->
            GameCarouselItem(game = game)
        }
    }
}