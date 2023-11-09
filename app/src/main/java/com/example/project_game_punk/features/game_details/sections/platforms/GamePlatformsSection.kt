package com.example.project_game_punk.features.game_details.sections.platforms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators

@Composable
fun GamePlatformsSection(
    gamePlatformsViewModel: GamePlatformsViewModel
) {
    val state = gamePlatformsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GamePlatformSectionLoadingState() }
    ) { platforms ->
        GamePlatformSectionLoadedState(platforms)
    }
}

@Composable
private fun GamePlatformSectionLoadingState() {
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(Color.DarkGray.copy(alpha = 0.3f))
        .height(40.dp)
    )
}

@Composable
private fun GamePlatformSectionLoadedState(
    platforms: List<GamePlatformEntity>
) {
    ItemCarousel(
        items = platforms,
        itemDecorator = ItemCarouselDecorators.pillItemDecorator
    ) {
        GamePlatformSectionItem(platform = it)
    }
}

@Composable
private fun GamePlatformSectionItem(platform: GamePlatformEntity) {
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
            text = platform.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}