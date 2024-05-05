package com.example.project_game_punk.features.game_details.sections.release_date

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
    )
}

@Composable
private fun GameReleaseDateSectionLoadedState(releaseDate: String?) {
    if (releaseDate == null) return
    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "Release Date",
            color = Color.LightGray,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier.width(6.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = releaseDate,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }
}