package com.example.project_game_punk.features.game_details.sections.platforms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.project_game_punk.features.common.composables.ItemCarousel
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper

@Composable
fun GamePlatformsSection(
    gamePlatformsViewModel: GamePlatformsViewModel
) {
    val state = gamePlatformsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { GamePlatformSectionFailedState(it) },
        loadingState = { GamePlatformSectionLoadingState() }
    ) { platforms ->
        GamePlatformSectionLoadedState(platforms)
    }
}


@Composable
private fun GamePlatformSectionFailedState(error: String) {
    Text(text = error, color = Color.White)
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
    ItemCarousel(items = platforms) {
        GamePlatformSectionItem(platform = it)
    }
}

@Composable
private fun GamePlatformSectionItem(platform: GamePlatformEntity) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = Color.White.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = platform.name,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}