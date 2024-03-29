package com.example.project_game_punk.features.game_details.sections.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
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
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel

@Composable
fun GameGenresSection(
    gameDetailsViewModel: GameDetailsViewModel,
    reload: () -> Unit = {}
) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state, failState = {
            GameGenresSectionFailedState {
                reload()
            }
        },
        loadingState = { GameGenresSectionLoadingState() }
    ) { game ->
        game?.gameGenres?.let { genres ->
            GameGenresSectionLoadedState(genres)
        }
    }
}


@Composable
private fun GameGenresSectionFailedState(reload: () -> Unit) {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .fillMaxWidth()
            .height(40.dp)) {
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
private fun GameGenresSectionLoadingState() {
    Column {
        val showShimmer = remember { mutableStateOf(true) }
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .height(40.dp)
        )
    }
}

@Composable
private fun GameGenresSectionLoadedState(
    genres: List<GameGenreEntity>
) {
    if (genres.isEmpty()) return
    Column {
        ItemCarousel(
            items = genres,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) {
            GameGenresSectionItem(genre = it)
        }
    }
}

@Composable
private fun GameGenresSectionItem(genre: GameGenreEntity) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White.copy(0.05f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}