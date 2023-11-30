package com.example.project_game_punk.features.discover.playing

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.R
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.game_details.GameDetailsActivity
import com.example.project_game_punk.features.game_details.largeRadialGradientBrush

@Composable
fun NowPlayingSection(nowPlayingViewModel: NowPlayingViewModel) {
    val state = nowPlayingViewModel.getState().observeAsState().value
    Column {
        SectionTitle(title = "Now playing")
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage ->
                NowPlayingFailedState(
                    errorMessage = errorMessage,
                    nowPlayingViewModel = nowPlayingViewModel
                )
            },
            loadingState = { NowPlayingSectionLoadingState() },
        ) { nowPlayingState ->
            NowPlayingSectionLoadedState(nowPlayingState = nowPlayingState)
        }
    }
}

@Composable
private fun NowPlayingFailedState(
    errorMessage: String,
    nowPlayingViewModel: NowPlayingViewModel
) {
    DiscoverGameFailState(errorMessage) {
        nowPlayingViewModel.loadState()
    }
}

@Composable
private fun NowPlayingSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(6.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
        )
        Box(modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(14.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value)))
    }
}

@Composable
private fun NowPlayingSectionLoadedState(nowPlayingState: NowPlayingState) {

    when (nowPlayingState) {
        is NowPlayingState.NowPlayingAvailable -> {
            NowPlayingAvailableState(
                nowPlayingAvailable = nowPlayingState
            )
        }
        is NowPlayingState.NowPlayingUnavailable -> {
            NowPlayingUnavailable(
               nowPlayingUnavailable = nowPlayingState
           )
        }
    }
}

@Composable
private fun NowPlayingUnavailable(
    nowPlayingUnavailable: NowPlayingState.NowPlayingUnavailable
) {
    val games = nowPlayingUnavailable.games
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(140.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black)
    ) {
        GamePunkGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            items = games,
            span = games.size
        ) { game ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.backgroundImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()

                .background(largeRadialGradientBrush(
                    listOf(
                        Color.Black.copy(alpha = 0.9f),
                        Color.Black.copy(alpha = 0.6f),
                    )
                ))

        )
                Text(
                    text = "No games being played",
//                    text = "You are not playing any games",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun NowPlayingAvailableState(
    nowPlayingAvailable: NowPlayingState.NowPlayingAvailable
) {
    val games = nowPlayingAvailable.nowPlayingGames
    ItemPagerCarousel(items = games) { game ->
        NowPlayingSectionItem(game = game)
    }
}

@Composable
private fun NowPlayingSectionItem(game: GameEntity) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .padding(6.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Black)
        .clickable {
            context.startActivity(
                Intent(
                    context,
                    GameDetailsActivity::class.java
                ).apply {
                    putExtra(
                        GameDetailsActivity.GAME_ID_INTENT_EXTRA,
                        game.id
                    )
                }
            )
        }
    ) {
        if (isAboveAndroid12()) {
            NowPlayingSectionItemBlurredBackground(
                game = game
            )
        } else {
            NowPlayingSectionItemGradientBackground(
                context = context,
                game = game
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NowPlayingSectionItemGameCover(game = game)
            game.name?.let { name ->
                Text(
                    modifier = Modifier.padding(
                        horizontal = 28.dp,
                        vertical = 6.dp
                    ),
                    text = name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun NowPlayingSectionItemGameCover(game: GameEntity) {
    AsyncImage(
        modifier = Modifier
            .size(
                110.dp,
                150.dp
            )
            .padding(12.dp)
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
private fun NowPlayingSectionItemBlurredBackground(game: GameEntity) {
    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .blur(45.dp)
            .clip(RoundedCornerShape(10.dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .data(game.backgroundImage)
            .crossfade(true)
            .build(),
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
    )
}


@Composable
private fun NowPlayingSectionItemGradientBackground(
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
        .blur(20.dp)
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

private fun isAboveAndroid12(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S || "S" == Build.VERSION.CODENAME
}