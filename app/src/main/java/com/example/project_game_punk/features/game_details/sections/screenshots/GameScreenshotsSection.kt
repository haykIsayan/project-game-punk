package com.example.project_game_punk.features.game_details.sections.screenshots

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight
import com.example.project_game_punk.ui.theme.steamPrimary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun GameScreenshotsSection(
    gameScreenshotsViewModel: GameScreenshotsViewModel
) {
    val state = gameScreenshotsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { screenshots ->
        GameScreenshotsSectionLoadedState(screenshots = screenshots)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun GameScreenshotsSectionLoadedState(screenshots: List<String>) {
    val state = rememberPagerState()

    Box(
//        verticalArrangement = Arrangement.SpaceEvenly,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        SectionTitle(title = "Screenshots")
        HorizontalPager(
            modifier = Modifier.align(Alignment.Center),
            count = screenshots.size,
            state = state
        ) { page ->
            val screenshot = screenshots[page]
            GameScreenshotItem(screenshot = screenshot)
        }

        HorizontalPagerIndicator(
            modifier = Modifier.padding(24.dp).align(Alignment.BottomCenter)
            ,
//                .align(Alignment.Center),
            pagerState = state,
            activeColor = Color.White,
            inactiveColor = Color.White.copy(alpha = 0.5f),
        )
    }
}


@Composable
private fun GameScreenshotItem(screenshot: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(screenshot)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}