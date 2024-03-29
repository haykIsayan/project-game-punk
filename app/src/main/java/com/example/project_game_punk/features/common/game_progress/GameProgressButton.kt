package com.example.project_game_punk.features.common.game_progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus

@Composable
fun GameProgressButton(
    game: GameEntity,
    modifier: Modifier,
    controller: GameProgressBottomSheetController,
    onProgressSelected: (GameEntity, GameProgressStatus) -> Unit
) {
    val gameProgressStatus = game.gameExperience?.gameProgressStatus ?: return
    val color = colorResource(GameProgressMapper.color(gameProgressStatus))
    val textColor = colorResource(GameProgressMapper.displayTextColor(gameProgressStatus))
    val borderColor =  if (gameProgressStatus == GameProgressStatus.notFollowing) {
        Color.White
    } else {
        colorResource(GameProgressMapper.color(gameProgressStatus))
    }
    val text = stringResource(GameProgressMapper.displayText(gameProgressStatus = gameProgressStatus)).uppercase()

    val onClick = {
        if (gameProgressStatus == GameProgressStatus.notFollowing) {
            onProgressSelected.invoke(game, GameProgressStatus.following)
        } else {
            controller.displayBottomSheet(game) { gameProgress ->
                onProgressSelected.invoke(game, gameProgress)
            }
        }
    }

    Box(modifier = modifier
        .clickable { onClick() }
        .height(30.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(
            1.dp,
            SolidColor(borderColor),
            shape = RoundedCornerShape(10.dp)
        )
        .background(color)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontSize = 10.sp,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            color = textColor
        )
    }
}
