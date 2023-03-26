package com.example.project_game_punk.features.game_details.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameHeaderBackgroundSection(screenshots: List<String>) {

    val state = rememberPagerState()
    val scope = rememberCoroutineScope()

    HorizontalPager(count = screenshots.size, state = state) { page ->
        val screenshot = screenshots[page]
        GameScreenshotItem(screenshot = screenshot)
    }

    LaunchedEffect(screenshots.size) {
        scope.launch(Dispatchers.IO) {
            while (state.currentPage < state.pageCount) {
                // Call scroll to on pagerState
                delay(3000)
                withContext(Dispatchers.Main) {
                    if (state.currentPage == state.pageCount - 1) {
                        state.animateScrollToPage(0, )
                    } else {
                        state.animateScrollToPage(state.currentPage + 1)
                    }
                }
            }
        }
    }




}

//
//private fun scroll(state: PagerState, ) {
//
//}


@Composable
private fun GameScreenshotItem(screenshot: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(screenshot)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}