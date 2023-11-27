package com.example.project_game_punk.features.game_details.sections.header

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel

@Composable
fun GameDetailsScore(gameDetailsViewModel: GameDetailsViewModel) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            GameDetailsScoreLoadingState()
        }
    ) { game ->
        game?.let {
            GameDetailsScoreLoadedState(it)
        }
    }
}

@Composable
private fun GameDetailsScoreLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .size(80.dp)
        .padding(12.dp)
        .aspectRatio(1f)
        .background(shimmerBrush(showShimmer = showShimmer.value), shape = CircleShape)
    )
}

@Composable
private fun GameDetailsScoreLoadedState(game: GameEntity) {
    val score = game.score.toFloat()
    val progress = remember { mutableStateOf(0f) }
    val progressAnimDuration = 1500
    val progressAnimation = animateFloatAsState(
        targetValue = progress.value,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
    )

    Box(modifier = Modifier
        .size(80.dp)
        .padding(12.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(80.dp),

            progress = progressAnimation.value  / 100f,

            color = Color.White,
        )
        Text(
            text = progressAnimation.value.toInt().toString(),
            modifier = Modifier.align(
                Alignment.Center
            ),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }

    LaunchedEffect(score) {
        progress.value = score
    }

}