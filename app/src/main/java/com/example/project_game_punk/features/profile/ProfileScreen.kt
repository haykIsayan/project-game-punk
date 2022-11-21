package com.example.project_game_punk.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.*
import com.example.project_game_punk.features.discover.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun ProfileScreen(searchViewModel: ProfileViewModel) {
    val state = searchViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { searchViewModel.loadState() } },
        loadingState = { DiscoverGameCarouselLoading() },
    ) { gameCollection ->
        Column {
            SectionTitle(title = "Profile")
            LazyColumn {
                items(gameCollection.games) { item ->
                    Column {
                        GameListItem(game = item)
                        Divider(color = Color.White, thickness = 0.2.dp)
                    }
                }
            }
        }
    }
}