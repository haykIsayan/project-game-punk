package com.example.project_game_punk.features.profile.artworks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.profile.ProfileLibraryViewModel
import com.example.project_game_punk.features.profile.ProfileSubHeaderSection
import com.example.project_game_punk.features.profile.ProfileUserViewModel
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileArtworkCarousel(
    profileLibraryViewModel: ProfileLibraryViewModel,
    profileArtworksViewModel: ProfileArtworksViewModel,
    profileUserViewModel: ProfileUserViewModel
) {
    val state = profileArtworksViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = {
            DiscoverGameFailState(it) {
                profileArtworksViewModel.loadState()
            }
        },
        loadingState = {
            ProfileArtworksSectionLoadingState()
        }
    ) { artworks ->
        ProfileArtworkCarouselSection(
            profileLibraryViewModel = profileLibraryViewModel,
            profileUserViewModel = profileUserViewModel,
            artworks
        )
    }

}

@Composable
private fun ProfileArtworksSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProfileArtworkCarouselSection(
    profileLibraryViewModel: ProfileLibraryViewModel,
    profileUserViewModel: ProfileUserViewModel,
    artworks: List<String>
) {
    if (artworks.isEmpty()) return

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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(gamePunkPrimaryDark.copy(alpha = 0.8f))
        )

        HeaderLyol(
            profileLibraryViewModel = profileLibraryViewModel,
            profileUserViewModel = profileUserViewModel
        )
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
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(screenshot)
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun HeaderLyol(
    profileLibraryViewModel: ProfileLibraryViewModel,
    profileUserViewModel: ProfileUserViewModel
) {
    Box(
        modifier = Modifier
//            .fillMaxSize()
            .fillMaxWidth()
            .height(220.dp)
//
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
//                .padding(12.dp)
//                .align(Alignment.Center)
        ) {
            ProfileSubHeaderSection(
                profileUserViewModel = profileUserViewModel,
                libraryViewModel = profileLibraryViewModel
            )
        }
    }
}