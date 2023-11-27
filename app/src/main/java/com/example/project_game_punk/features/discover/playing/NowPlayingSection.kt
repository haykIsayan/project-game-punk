package com.example.project_game_punk.features.discover.playing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.GamePagerCarouselItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun NowPlayingSection(nowPlayingViewModel: NowPlayingViewModel) {
    val state = nowPlayingViewModel.getState().observeAsState().value
    Column {
        SectionTitle(title = "Now playing")
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage ->
                NowPlayingFailedState(
                    errorMessage = errorMessage,
                    nowPlayingViewModel = nowPlayingViewModel
                )
            },
            loadingState = { NowPlayingSectionLoadingState() },
        ) { games ->
            ItemPagerCarousel(items = games) { game ->
                GamePagerCarouselItem(game = game)
            }
        }
    }
}

@Composable
private fun NowPlayingFailedState(
    errorMessage: String,
    nowPlayingViewModel: NowPlayingViewModel
) {
    DiscoverGameFailState(errorMessage) {
        nowPlayingViewModel.loadState()
    }
}

@Composable
private fun NowPlayingSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(6.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
        )
        Box(modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(14.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value)))
    }
}