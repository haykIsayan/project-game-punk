package com.example.project_game_punk.features.game_details.sections.header

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.R
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameDetailsViewModel


@Composable
fun GameDetailsCover(gameDetailsViewModel: GameDetailsViewModel, onColorLoaded: (Int) -> Unit) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameCoverLoadingState() }
    ) { game ->
        game?.let { GameCoverLoadedState(game = game, onColorLoaded) }
    }
}


@Composable
private fun GameCoverLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .size(
                150.dp,
                200.dp
            )
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
    )
}


@Composable
private fun GameCoverLoadedState(game: GameEntity, onColorLoaded: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(
                150.dp,
                200.dp
            )
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))) {

        val context = LocalContext.current


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
                        onColorLoaded.invoke(dominantColor)
                    }
                }
            }
            .build()

        loader.enqueue(req)

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