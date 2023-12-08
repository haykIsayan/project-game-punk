package com.example.project_game_punk.features.game_details.sections.age_rating

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameAgeRatingEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper

@Composable
fun GameAgeRatingSection(
    gameAgeRatingViewModel: GameAgeRatingViewModel
) {
    val state = gameAgeRatingViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameAgeRatingSectionLoadingState() }
    ) { gameAgeRating ->
        gameAgeRating ?.let {
            GameAgeRatingSectionLoadedState(gameAgeRating)
        }
    }
}


@Composable
private fun GameAgeRatingSectionLoadingState() {

}

@Composable
private fun GameAgeRatingSectionLoadedState(gameAgeRating: GameAgeRatingEntity) {
    Box(
        modifier = Modifier
            .size(65.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(gameAgeRating.logo)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
        )
    }
}