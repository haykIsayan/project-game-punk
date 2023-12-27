package com.example.project_game_punk.features.game_details.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.game_details.GameDetailsViewModel

@Composable
fun UserScoreSection(
    gameDetailsViewModel: GameDetailsViewModel
) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { game ->
        game?.gameExperience?.let { experience ->
            val score = experience.userScore
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(12.dp).align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 1..5) {
                        val isFilled = (score / 20).toInt() >= i
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    val newScore = i * 20f
                                    gameDetailsViewModel.updateUserScore(
                                        game,
                                        newScore
                                    )
                                },
                            imageVector = if (isFilled)
                                Icons.Filled.Star
                            else
                                Icons.Filled.StarBorder,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}