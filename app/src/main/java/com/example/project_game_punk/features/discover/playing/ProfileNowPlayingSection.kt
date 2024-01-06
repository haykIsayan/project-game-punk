package com.example.project_game_punk.features.discover.playing

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.project_game_punk.features.common.composables.GameUserScoreDisplay
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.game_details.GameDetailsActivity
import com.example.project_game_punk.features.game_details.largeRadialGradientBrush
import com.example.project_game_punk.ui.theme.gamePunkPrimary

@Composable
fun ProfileNowPlayingSection(nowPlayingViewModel: NowPlayingViewModel) {
    val state = nowPlayingViewModel.getState().observeAsState().value


    LoadableStateWrapper(
        state = state,
        failState = { errorMessage ->
            NowPlayingFailedState(
                errorMessage = errorMessage,
                nowPlayingViewModel = nowPlayingViewModel
            ) },
        loadingState = { NowPlayingSectionLoadingState() },
    ) { nowPlayingState ->
        Column {
            SectionTitle(title = "Now Playing") {

            }
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
        SectionTitle(
            title = "Now Playing",
            isLoading = true
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
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
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(12.dp)
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

                .background(
                    largeRadialGradientBrush(
                        listOf(
                            gamePunkPrimary.copy(alpha = 0.9f),
                            gamePunkPrimary.copy(alpha = 0.6f),
                        )
                    )
                )
        )
        Text(
            text = "No games being played",
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
        .height(160.dp)
        .padding(12.dp)
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
        NowPlayingSectionItemGradientBackground(
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
            NowPlayingSectionItemGameCover(game = game)
            Column(
                modifier = Modifier
                    .padding(
                    horizontal = 28.dp,
                    vertical = 6.dp
                ),
            ) {
                GameUserScoreDisplay(game = game)
                Spacer(modifier = Modifier.height(6.dp))
                game.name?.let { name ->
                    Text(
                        text = name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                NowPlayingPlatformInfo(game = game)
                Spacer(modifier = Modifier.height(6.dp))
                NowPlayingStoreInfo(game = game)
            }
        }
    }
}

@Composable
private fun NowPlayingPlatformInfo(game: GameEntity) {
    game.gamePlatforms?.find {
        it.id == game.gameExperience?.platformId
    }?.let { platform ->
        Text(
            text = platform.name,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun NowPlayingStoreInfo(game: GameEntity) {
    val imageResource = when (game.gameExperience?.storeId) {
        "xbox_marketplace" -> R.drawable.ic_xbox_marketplace
        "microsoft" -> R.drawable.ic_microsoft
        "steam" -> R.drawable.ic_steam
        "epic_game_store" -> R.drawable.ic_epic_games
        "xbox_game_pass_ultimate_cloud" -> R.drawable.ic_xbox_game_pass
        "playstation_store_us" -> R.drawable.ic_playstation_store
        "amazon" -> R.drawable.ic_amazon
        else -> null
    }
    if (imageResource != null) {
        Image(
            modifier = Modifier
                .size(35.dp),
            painter = painterResource(imageResource),
            contentDescription = "Content description for visually impaired"
        )
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