package com.example.project_game_punk.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.*
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.discover.DiscoverGameCarouselLoading
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
            SectionTitle(title = "Your games")
            LazyColumn {
                items(games) { game ->
                    Column {
                        GameListItem(game = game,
                            trailingButton = {
                                GameProgressButton(
                                    game = game,
                                    modifier = Modifier.padding(8.dp),
                                    controller = controller,
                                    onProgressSelected = { game, gameProgress ->
                                        profileViewModel.updateGameProgress(game, gameProgress)
                                    }
                                )
                            }
                        )
                        Divider(color = Color.White, thickness = 0.2.dp)
                    }
                }
            }
        }
    }
}