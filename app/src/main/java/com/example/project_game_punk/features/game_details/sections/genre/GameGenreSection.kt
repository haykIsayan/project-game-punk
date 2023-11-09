package com.example.project_game_punk.features.game_details.sections.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators

@Composable
fun GameGenresSection(
    gameGenresViewModel: GameGenresViewModel
) {
    val state = gameGenresViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { GameGenresSectionFailedState(it) },
        loadingState = { GameGenresSectionLoadingState() }
    ) { genres ->
        GameGenresSectionLoadedState(genres)
    }
}


@Composable
private fun GameGenresSectionFailedState(error: String) {
//    Text(text = error, color = Color.White)
}


@Composable
private fun GameGenresSectionLoadingState() {
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(Color.DarkGray.copy(alpha = 0.3f))
        .height(40.dp)
    )
}

@Composable
private fun GameGenresSectionLoadedState(
    genres: List<GameGenreEntity>
) {
    ItemCarousel(
        items = genres,
        itemDecorator = ItemCarouselDecorators.pillItemDecorator
    ) {
        GameGenresSectionItem(genre = it)
    }
}

@Composable
private fun GameGenresSectionItem(genre: GameGenreEntity) {
    Box(
        modifier = Modifier
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}