package com.example.project_game_punk.features.discover.playing

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.discover.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselSection
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun NowPlayingSection(nowPlayingViewModel: NowPlayingViewModel) {
    val state = nowPlayingViewModel.getState().observeAsState().value
    Column {
        SectionTitle(title = "Now playing")
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage -> DiscoverGameFailState(errorMessage) { nowPlayingViewModel.loadState() } },
            loadingState = { DiscoverGameCarouselLoading() },
        ) { games ->
            ItemCarousel(items = games) { game ->
                GameCarouselItem(
                    game = game,
                    sheetController = null
                ) { game, gameProgress ->

                }
            }
        }
    }
}