package com.example.project_game_punk.features.game_details.sections.dlc

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
fun GameDLCSection(
    gameDLCsViewModel: GameDLCsViewModel,
    reload: () -> Unit = {},
) {
    val state = gameDLCsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState =  {
            GameDLCsSectionFailedState {
                reload()
            }
        },
        loadingState = { GameDLCSectionLoadingState() }
    ) { stores ->
        GameDLCSectionLoadedState(stores)
    }
}

@Composable
private fun GameDLCsSectionFailedState(reload: () -> Unit) {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(title = "Add Ons")
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .fillMaxWidth()
            .height(200.dp)) {
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
private fun GameDLCSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
                .fillMaxWidth()
                .height(22.dp)
        )
        LazyRow(content = {
            items(4) {
                Box(modifier = Modifier
                    .size(
                        150.dp,
                        200.dp
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
private fun GameDLCSectionLoadedState(dlcs: List<GameEntity>) {
    if (dlcs.isEmpty()) return
    Column {
        SectionTitle(title = "Add Ons")
        ItemCarousel(items = dlcs) { game ->
            GameCarouselItem(game = game)
        }
    }
}