package com.example.project_game_punk.features.common.game_progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.models.GameModel

@Composable
fun GameProgressButton(
    game: GameModel,
    modifier: Modifier,
    controller: GameProgressBottomSheetController,
    onProgressSelected: (GameModel, GameProgress) -> Unit
) {
    val context = LocalContext.current
    val color = colorResource(game.gameProgress.color).copy(alpha = 0.6f)
    val textColor = Color.White
    val borderColor =  Color.White
    val text = context.getString(game.gameProgress.displayText).uppercase()

    val onClick = {
        if (game.gameProgress == GameProgress.NotFollowingGameProgress) {
            onProgressSelected.invoke(game, GameProgress.FollowingGameProgress)
        } else {
            controller.displayBottomSheet(game) { gameProgress ->
                onProgressSelected.invoke(game, gameProgress)
            }
        }
    }

    Button(
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,

        ),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color,
        ),
        modifier = modifier.height(30.dp)
    ) {
        Text(
            modifier = Modifier.background(Color.Transparent),
            text = text,
            fontSize = 10.sp,
            color = textColor
        )
    }
}
