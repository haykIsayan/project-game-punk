package com.example.project_game_punk.features.discover.recommended

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun RecommendedGamesSection(
    viewModel: RecommendedGamesViewModel,
    sheetController: GameProgressBottomSheetController
) {
    val state = viewModel.getState().observeAsState().value
    Column {
        SectionTitle(title = "Games for you")
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage -> DiscoverGameFailState(errorMessage) { viewModel.loadState() } },
            loadingState = { DiscoverGameCarouselLoading() },
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