package com.example.project_game_punk.features.game_details.sections.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark


@Composable
fun GameDetailsTitle(
    modifier: Modifier,
    gameDetailsViewModel: GameDetailsViewModel,
    onBackPressed: () -> Unit
) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
//            GameTitleLoadingState()
        }
    ) { game ->
        game?.let {
            GameTitleLoadedState(
                modifier,
                game = game,
                onBackPressed = onBackPressed,
            )
        }
    }
}

@Composable
private fun GameTitleLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(shimmerBrush(showShimmer = showShimmer.value))
        .height(20.dp)
    )
}

@Composable
private fun GameTitleLoadedState(
    modifier: Modifier,
    game: GameEntity,
    onBackPressed: () -> Unit,
) {
    game.name?.let { name ->

        Box( modifier
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
            .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    gamePunkPrimaryDark.copy(alpha = 0.5f),
                    gamePunkPrimaryDark.copy(alpha = 0.2f),
                    gamePunkPrimaryDark,
                )
            )
        )) {
            Text(
                text = name,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = Color.White,
            )
        }

    }
}