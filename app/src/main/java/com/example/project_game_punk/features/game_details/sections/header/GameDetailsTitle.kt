package com.example.project_game_punk.features.game_details.sections.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel


@Composable
fun GameDetailsTitle(
    gameDetailsViewModel: GameDetailsViewModel,
    onBackPressed: () -> Unit
) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameTitleLoadingState() }
    ) { game ->
        game?.let {
            GameTitleLoadedState(
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
    game: GameEntity,
    onBackPressed: () -> Unit,
) {
    game.name?.let {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
                    .clickable { onBackPressed() },
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                tint = Color.White
            )
            Text(
                text = it,
                modifier = Modifier
                    .weight(6f)
                    .padding(6.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Box(modifier = Modifier.weight(1f))
        }
    }
}