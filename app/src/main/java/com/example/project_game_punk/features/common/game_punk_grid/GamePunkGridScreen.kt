package com.example.project_game_punk.features.common.game_punk_grid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.ScreenTitle
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.common.composables.grids.GamePunkGridLoading
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.main.GamePunkNavigator

@Composable
fun GamePunkGridScreen(
    title: String,
    stateViewModel: StateViewModel<List<GameEntity>, String>,
) {

    LaunchedEffect(Unit) {
        stateViewModel.loadState(force = true)
    }

    val state = stateViewModel.getState().observeAsState().value
    Column {
        ScreenTitle(
            leading = {
                Icon(
                    modifier = Modifier.clickable {
                        GamePunkNavigator.goBack()
                    },
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = ""
                )
            },
            title = title
        )
        LoadableStateWrapper(state = state) { games ->
            GamePunkGrid(
                modifier = Modifier,
                span = 3,
                items = games
            ) { game ->
                GameCarouselItem(game = game)
            }
        }
    }
}