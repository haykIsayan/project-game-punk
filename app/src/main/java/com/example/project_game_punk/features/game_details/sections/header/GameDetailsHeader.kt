package com.example.project_game_punk.features.game_details.sections.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel
import com.example.project_game_punk.features.game_details.sections.GameHeaderBackgroundSection
import com.example.project_game_punk.features.game_details.sections.developer_publisher.GameDeveloperPublisherSection
import com.example.project_game_punk.features.game_details.sections.developer_publisher.GameDeveloperPublisherViewModel
import com.example.project_game_punk.features.game_details.sections.release_date.GameReleaseDateSection
import com.example.project_game_punk.features.game_details.sections.release_date.GameReleaseDateViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel


@Composable
fun GameDetailsHeader(
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel,
    onColorLoaded: (Int) -> Unit
) {
    Column {
        GameScreenshotsPager(
            gameScreenshotsViewModel,
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GameDetailsCover(
                gameDetailsViewModel,
                onColorLoaded
            )
            GameDetailsAdditional(
                gameDetailsViewModel,
                gameDeveloperPublisherViewModel,
                gameReleaseDateViewModel
            )
        }
    }
}

@Composable
private fun GameScreenshotsPager(
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {
    val state = gameScreenshotsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameScreenshotsPagerLoadingState() }
    ) { screenshots ->
        GameHeaderBackgroundSection(screenshots = screenshots)
    }
}

@Composable
private fun GameScreenshotsPagerLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(220.dp)
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(shimmerBrush(showShimmer = showShimmer.value))
    )
}

@Composable
private fun GameDetailsAdditional(
    gameDetailsViewModel: GameDetailsViewModel,
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel,
    gameReleaseDateViewModel: GameReleaseDateViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GameReleaseDateSection(
                gameReleaseDateViewModel
            )
            Box(
                modifier = Modifier
                    .width(0.3.dp)
                    .height(30.dp)
                    .background(Color.White)
            )
            GameDetailsScore(
                gameDetailsViewModel
            )
        }
        GameDeveloperPublisherSection(
            gameDeveloperPublisherViewModel
        )
    }
}

