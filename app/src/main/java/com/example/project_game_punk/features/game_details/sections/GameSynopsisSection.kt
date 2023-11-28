package com.example.project_game_punk.features.game_details.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
        for (i in 0..4) {
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
    val expanded = remember { mutableStateOf(false) }
    game?.description?.let {
        Column {
            SectionTitle(title = "Description")
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = if (expanded.value) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(12.dp)
                        .animateContentSize()
                )
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            expanded.value = !expanded.value
                                   },
                    imageVector = if (expanded.value) {
                        Icons.Filled.ExpandLess
                    } else {
                        Icons.Filled.ExpandMore
                           },
                    tint = Color.White,
                    contentDescription = ""
                )
            }
        }
    }
}