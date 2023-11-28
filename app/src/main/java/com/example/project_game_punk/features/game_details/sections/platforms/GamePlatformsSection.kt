package com.example.project_game_punk.features.game_details.sections.platforms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.shimmerBrush

@Composable
fun GamePlatformsSection(
    gamePlatformsViewModel: GamePlatformsViewModel,
    reload: () -> Unit = {}
) {
    val state = gamePlatformsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = {
            GamePlatformSectionFailedState {
                reload()
            }
        },
        loadingState = { GamePlatformSectionLoadingState() }
    ) { platforms ->
        GamePlatformSectionLoadedState(platforms)
    }
}


@Composable
private fun GamePlatformSectionFailedState(reload: () -> Unit) {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(title = "Platforms")
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .fillMaxWidth()
            .height(40.dp)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp)
                    .clickable {
                        reload()
                    },
                imageVector = Icons.Filled.Sync,
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun GamePlatformSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(shimmerBrush(showShimmer = showShimmer.value))
        .height(40.dp)
    )
}

@Composable
private fun GamePlatformSectionLoadedState(
    platforms: List<GamePlatformEntity>
) {
    if (platforms.isEmpty()) return
    Column {
        SectionTitle(title = "Platforms")
        ItemCarousel(
            items = platforms,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) {
            GamePlatformSectionItem(platform = it)
        }
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