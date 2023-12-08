package com.example.project_game_punk.features.discover.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle

@Composable
fun DiscoverGameCarouselSection(title: String, viewModel: StateViewModel<List<GameEntity>, Unit>) {
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