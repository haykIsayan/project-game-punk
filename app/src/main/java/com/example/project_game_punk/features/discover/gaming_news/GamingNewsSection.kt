package com.example.project_game_punk.features.discover.gaming_news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.discover.updates_patches.GameNewsEntityState

@Composable
fun GamingNewsSection(
    gamingNewsViewModel: GamingNewsViewModel
) {
    val state = gamingNewsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = {
        },
        loadingState = {
            GamingNewsSectionLoadingState()
        }
    ) { states ->
        GamingNewsSectionLoadedState(states)
    }
}

@Composable
private fun GamingNewsSectionLoadingState() {
    Column {
        SectionTitle(
            title = "Gaming news",
            isLoading = true
        )
        LazyRow(content = {
            items(4) {index ->
                val showShimmer = remember { mutableStateOf(true) }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .width(280.dp)
                        .height(160.dp)
                        .padding(
                            start = if (index == 0) 12.dp else 6.dp,
                            end = 6.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value))
                    )
//                    Box(modifier = Modifier
//                        .width(120.dp)
//                        .height(40.dp)
//                        .padding(
//                            start = if (index == 0) 12.dp else 6.dp,
//                            end = 6.dp,
//                            top = 6.dp,
//                            bottom = 6.dp
//                        )
//                        .clip(RoundedCornerShape(10.dp))
//                        .background(shimmerBrush(showShimmer = showShimmer.value)))
                }
            }
        })
    }
}

@Composable
fun GamingNewsSectionLoadedState(states: List<GamingNewsEntityState>) {
    Column {
        SectionTitle(title = "Gaming news")

        ItemCarousel(
            items = states,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) { state ->
            GamingNewsItem(state)
        }
    }
}

@Composable
private fun GamingNewsItem(state: GamingNewsEntityState) {
    val artwork = state.artwork
    val game = state.game
    val news = state.gameNews ?: return


    Column {
        if (artwork.isNullOrEmpty()) {
            Box(modifier = Modifier
                .width(280.dp)
                .height(160.dp)
//                .padding(
//                    start = /*if (index == 0) 12.dp else*/ 6.dp,
//                    end = 6.dp,
//                    top = 6.dp,
//                    bottom = 6.dp
//                )
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = true))
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .width(280.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artwork)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,

                )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .width(280.dp)
//                .height(100.dp)
//                .clip(RoundedCornerShape(10.dp))
//                .background(Color.White.copy(alpha = 0.05f))
            ,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
//            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = news.author,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier
                    .width(280.dp)
                    .height(50.dp),
                text = news.title,
                maxLines = 3,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold
            )
//            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}