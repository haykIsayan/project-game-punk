package com.example.project_game_punk.features.game_details.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel

@Composable
fun GameSynopsisSection(gameDetailsViewModel: GameDetailsViewModel) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
        GameSynopsisSectionLoadingState()
    }) {
        GameSynopsisSectionLoadedState(game = it)
    }
}

@Composable
private fun GameSynopsisSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        for (i in 0..5) {
            Box(modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .background(
                    shimmerBrush(
                        targetValue = 1000f,
                        showShimmer = showShimmer.value
                    )
                )
                .height(10.dp)
            )
        }
    }
}

@Composable
private fun GameSynopsisSectionLoadedState(game: GameEntity?) {
    val interactionSource = remember { MutableInteractionSource() }
    val isOverflowing = remember { mutableStateOf(false) }
    val isExpanded = remember { mutableStateOf(false) }
    val rotation = remember { mutableStateOf(0f) }
    val rotationAnimDuration = 300
    val rotationAnimation = animateFloatAsState(
        targetValue = rotation.value,
        animationSpec = tween(durationMillis = rotationAnimDuration)
    )
    game?.description?.let {
        Column {
            SectionTitle(title = "Description")
            Text(
                text = it,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = if (isExpanded.value) Int.MAX_VALUE else 5,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = {
                    isOverflowing.value = it.didOverflowHeight
                },
                modifier = Modifier
                    .padding(12.dp)
                    .animateContentSize(),
            )
            if (isOverflowing.value || isExpanded.value) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .rotate(rotationAnimation.value)
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                isExpanded.value = !isExpanded.value
                                if (rotation.value == 0f) {
                                    rotation.value = 180f
                                } else {
                                    rotation.value = 0f
                                }
                            },
                        imageVector = Icons.Filled.ExpandMore,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}