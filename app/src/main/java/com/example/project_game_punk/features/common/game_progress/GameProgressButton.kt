package com.example.project_game_punk.features.common.game_progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgress

@Composable
fun GameProgressButton(
    game: GameEntity,
    modifier: Modifier,
    controller: GameProgressBottomSheetController,
    onProgressSelected: (GameEntity, GameProgress) -> Unit
) {
    val gameProgress = game.gameProgress
    val color = colorResource(GameProgressMapper.color(gameProgress))
    val textColor = colorResource(GameProgressMapper.displayTextColor(gameProgress))
    val borderColor =  Color.White
    val text = stringResource(GameProgressMapper.displayText(gameProgress = gameProgress)).uppercase()

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
        shape = RoundedCornerShape(10.dp),
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
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
