package com.example.project_game_punk.features.game_details.sections.release_date

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush

@Composable
fun GameReleaseDateSection(gameReleaseDateViewModel: GameReleaseDateViewModel) {
    val state = gameReleaseDateViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            GameReleaseDateSectionLoadingState()
        }
    ) { releaseDate ->
        GameReleaseDateSectionLoadedState(releaseDate)
    }
}

@Composable
private fun GameReleaseDateSectionLoadingState() {
    val showShimmer = remember {
        mutableStateOf(true)
    }
    Box {
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value)),
            text = "Jun 27, 2023".uppercase(),
            color = Color.Transparent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun GameReleaseDateSectionLoadedState(releaseDate: String?) {
    if (releaseDate == null) return
    Text(
        modifier = Modifier.padding(horizontal = 12.dp),
        text = releaseDate.uppercase(),
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}