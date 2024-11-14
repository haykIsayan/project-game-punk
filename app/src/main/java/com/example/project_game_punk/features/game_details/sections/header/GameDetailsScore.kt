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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.game.GameEntity
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
        .size(60.dp)
        .padding(horizontal = 12.dp, vertical = 4.dp)
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






    Row(
        modifier = Modifier.padding(horizontal = 12.dp, /*vertical = 4.dp*/),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "Aggregate Score",
            color = Color.LightGray,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier
            .alignByBaseline()
            .size(60.dp)
            .padding(12.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(60.dp),
                progress = progressAnimation.value  / 100f,
                strokeWidth = 2.dp,
                color = Color.White,
            )
            Text(
                text = progressAnimation.value.toInt().toString(),
                modifier = Modifier.align(
                    Alignment.Center
                ),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }



    LaunchedEffect(score) {
        progress.value = score
    }
}