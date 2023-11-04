package com.example.project_game_punk.features.game_details.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.game_details.GameDetailsViewModel
import com.example.project_game_punk.features.game_details.sections.screenshots.GameScreenshotsViewModel


@Composable
fun GameDetailsHeader(
    gameDetailsViewModel: GameDetailsViewModel,
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)) {
        GameScreenshotsPager(gameScreenshotsViewModel = gameScreenshotsViewModel)
        Row(
            modifier = Modifier.align(Alignment.BottomStart),
            verticalAlignment = Alignment.Bottom
        ) {
            GameCover(gameDetailsViewModel = gameDetailsViewModel)
            GameTitle(gameDetailsViewModel = gameDetailsViewModel)
        }
    }
}

@Composable
private fun GameScreenshotsPager(gameScreenshotsViewModel: GameScreenshotsViewModel) {
    val state = gameScreenshotsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state, failState = {

    }, loadingState = {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.DarkGray.copy(alpha = 0.3f))
            .height(220.dp)
        )
    }) { screenshots ->
       GameHeaderBackgroundSection(screenshots = screenshots)
    }
}

@Composable
private fun GameCover(gameDetailsViewModel: GameDetailsViewModel) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state, failState = {

    }, loadingState = {
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .size(
                150.dp,
                200.dp
            )
            .background(Color.DarkGray)
        )
    }) { game ->
        game?.let {
            Box(
                modifier = Modifier
                    .size(
                        150.dp,
                        200.dp
                    )
                    .padding(12.dp)
                    .clip(RoundedCornerShape(10.dp))) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.backgroundImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                )
            }
        }
    }
}

@Composable
private fun GameTitle(gameDetailsViewModel: GameDetailsViewModel) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state, failState = {

    }, loadingState = {
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.DarkGray)
            .height(60.dp)
        )
    }) { game ->
        game?.name?.let {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = it,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}