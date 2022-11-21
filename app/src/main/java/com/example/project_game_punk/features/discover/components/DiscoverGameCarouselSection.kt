package com.example.project_game_punk.features.discover.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.discover.DiscoverGameCarouselLoading
import com.example.project_game_punk.domain.models.GameModel

@Composable
fun DiscoverGameCarouselSection(title: String, viewModel: StateViewModel<List<GameModel>, Unit>) {
    val state = viewModel.getState().observeAsState().value
    Column {
        SectionTitle(title = title)
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage -> DiscoverGameFailState(errorMessage) { viewModel.loadState() } },
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