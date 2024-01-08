package com.example.project_game_punk.features.discover.news

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.project_game_punk.R
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.game_details.GameDetailsActivity
import com.example.project_game_punk.features.game_details.GameWebViewActivity
import com.example.project_game_punk.features.game_details.largeRadialGradientBrush

@Composable
fun GameNewsSection(gameNewsViewModel: GameNewsViewModel) {
    val state = gameNewsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { gameNewsViewModel.loadState() } },
        loadingState = { GameNewsSectionLoadingState() },
    ) { gameNewsStates ->
        Column {
            SectionTitle(title = "What's New")
            GameNewsSectionLoadedState(gameNewsStates = gameNewsStates)
        }
    }
}


@Composable
private fun GameNewsSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(
            title = "What's New",
            isLoading = true
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
        )
        Box(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(14.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value)))
    }
}

@Composable
private fun GameNewsSectionLoadedState(gameNewsStates: List<GameNewsEntityState>) {
    ItemPagerCarousel(items = gameNewsStates) { gameNewsState ->
        GameNewsCarouselItem(
            game = gameNewsState.game,
            gameNews = gameNewsState.gameNews
        )
    }
}

@Composable
fun GameNewsCarouselItem(
    game: GameEntity,
    gameNews: GameNewsEntity
) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Black)
        .clickable {
            context.startActivity(
                Intent(
                    context,
                    GameWebViewActivity::class.java
                ).apply {
                    putExtra(
                        GameWebViewActivity.URL_INTENT_EXTRA,
                        gameNews.url
                    )
                }
            )
        }
    ) {
        GameNewsSectionItemGradientBackground(
            context = context,
            game = game
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GameNewsSectionItemGameCover(game = game)
            Column(
                modifier = Modifier.padding(
                    horizontal = 28.dp,
                    vertical = 12.dp
                )
            ) {
                game.name?.let { name ->
                    Text(
                        text = name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = gameNews.title,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = gameNews.author,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = gameNews.date,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun GameNewsSectionItemGameCover(game: GameEntity) {
    val context = LocalContext.current
    AsyncImage(
        modifier = Modifier
            .size(
                110.dp,
                150.dp
            )
            .padding(12.dp)
            .clickable {
                game.id?.let { gameId ->
                    context.startActivity(
                        Intent(
                            context,
                            GameWebViewActivity::class.java
                        ).apply {
                            putExtra(
                                GameDetailsActivity.GAME_ID_INTENT_EXTRA,
                                gameId
                            )
                        }
                    )
                }
            }
            .clip(RoundedCornerShape(10.dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .data(game.backgroundImage)
            .crossfade(true)
            .build(),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun GameNewsSectionItemGradientBackground(
    context: Context,
    game: GameEntity
) {
    val backgroundColor = remember { mutableStateOf(0) }
    val loader = ImageLoader(LocalContext.current)
    val req = ImageRequest.Builder(LocalContext.current)
        .data(game.backgroundImage)
        .allowHardware(false)
        .target { result ->
            val bitmap = (result as BitmapDrawable).bitmap
            Palette.from(bitmap).generate {
                it?.let { palette ->
                    val dominantColor = palette.getDominantColor(
                        ContextCompat.getColor(
                            context,
                            R.color.black
                        )
                    )
                    backgroundColor.value = dominantColor
                }
            }
        }
        .build()
    loader.enqueue(req)
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            largeRadialGradientBrush(
                listOf(
                    Color(backgroundColor.value).copy(alpha = 0.9f),
                    Color(backgroundColor.value).copy(alpha = 0.7f),
                )
            )
        )
    )
}
