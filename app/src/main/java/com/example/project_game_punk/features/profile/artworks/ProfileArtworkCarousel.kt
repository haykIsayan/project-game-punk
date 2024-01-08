package com.example.project_game_punk.features.profile.artworks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileArtworkCarousel(profileArtworksViewModel: ProfileArtworksViewModel) {
    val state = profileArtworksViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { artworks ->
        ProfileArtworkCarouselSection(artworks)
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProfileArtworkCarouselSection(artworks: List<String>) {

    val state = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        HorizontalPager(
            count = artworks.size,
            state = state
        ) { page ->
            val screenshot = artworks[page]
            GameScreenshotItem(screenshot = screenshot)
        }
    }

    LaunchedEffect(artworks.size) {
        scope.launch(Dispatchers.IO) {
            while (state.currentPage < state.pageCount) {
                // Call scroll to on pagerState
                delay(5000)
                withContext(Dispatchers.Main) {
                    if (state.currentPage == state.pageCount - 1) {
                        state.animateScrollToPage(0)
                    } else {
                        state.animateScrollToPage(state.currentPage + 1)
                    }
                }
            }
        }
    }
}


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