package com.example.project_game_punk.features.game_details.sections.experience

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.project_game_punk.features.game_details.GameDetailsViewModel


@Composable
fun GameExperienceUserScoreAndFavorite(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GameExperienceUserScore(
            game = game,
            gameExperience = gameExperience,
            gameDetailsViewModel = gameDetailsViewModel
        )
        GameExperienceFavorite(
            game = game,
            gameExperience = gameExperience,
            gameDetailsViewModel = gameDetailsViewModel
        )
    }
}

@Composable
private fun GameExperienceUserScore(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val score = gameExperience.userScore
    Row(
        modifier = Modifier.padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val scoreIndexDiff = (score / 20f) - i.toFloat()
            val star = when {
                scoreIndexDiff >= 0 -> Icons.Filled.Star
                scoreIndexDiff == -0.5f -> Icons.Filled.StarHalf
                else -> Icons.Filled.StarBorder
            }
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        when (star) {
                            Icons.Filled.Star -> {
                                val newScore = (i - 0.5f) * 20f
                                gameDetailsViewModel.updateUserScore(
                                    game,
                                    newScore
                                )
                            }

                            Icons.Filled.StarHalf -> {
                                val newScore = (i - 1) * 20f
                                gameDetailsViewModel.updateUserScore(
                                    game,
                                    newScore
                                )
                            }

                            else -> {
                                val newScore = i * 20f
                                gameDetailsViewModel.updateUserScore(
                                    game,
                                    newScore
                                )
                            }
                        }
                    },
                imageVector = star,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun GameExperienceFavorite(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val isFavorite = gameExperience.favorite
    Icon(
        modifier = Modifier
            .size(55.dp)
            .padding(12.dp)
            .clickable {
                gameDetailsViewModel.updateIsFavorite(game)
            },
        imageVector = if (isFavorite == true)
            Icons.Filled.Favorite
        else
            Icons.Filled.FavoriteBorder,
        contentDescription = "",
        tint = Color.White
    )
}