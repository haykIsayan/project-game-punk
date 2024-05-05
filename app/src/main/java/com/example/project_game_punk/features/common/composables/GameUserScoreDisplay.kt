package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameEntity

@Composable
fun GameUserScoreDisplay(game: GameEntity) {
    val score = game.gameExperience?.userScore ?: return
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val scoreIndexDiff = (score / 20f) - i.toFloat()
            val star = when {
                scoreIndexDiff >= 0 -> Icons.Filled.Star
                scoreIndexDiff == -0.5f -> Icons.Filled.StarHalf
                else -> null
            }
            if (star != null) {
                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = star,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}