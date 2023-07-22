package com.example.project_game_punk.features.game_details.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.game_details.GameDetailsViewModel

@Composable
fun GameSynopsisSection(gameDetailsViewModel: GameDetailsViewModel) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = {

    }, loadingState = {
        GameSynopsisSectionLoadingState()
    }) {
        GameSynopsisSectionLoadedState(game = it)
    }
}


@Composable
private fun GameSynopsisSectionLoadingState() {
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(Color.DarkGray.copy(alpha = 0.3f))
        .height(40.dp)
    )
}

@Composable
private fun GameSynopsisSectionLoadedState(game: GameEntity?) {
    game?.description?.let {
        Text(
            text = it,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}