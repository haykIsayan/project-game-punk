package com.example.project_game_punk.features.profile

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.*
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = profileViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { profileViewModel.loadState() } },
        loadingState = { DiscoverGameCarouselLoading() },
    ) { games ->
        Column {
            SectionTitle(title = "Your Games")
            GamePunkGrid(
                modifier = Modifier/*.padding(6.dp)*/,
                items = games,
                span = 3,
                footer = {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            ) {
                GameCarouselItem(
                    game = it,
                    sheetController = controller
                ) { game, gameProgress ->
                    profileViewModel.updateGameProgress(game, gameProgress)
                }
            }
        }
    }
}