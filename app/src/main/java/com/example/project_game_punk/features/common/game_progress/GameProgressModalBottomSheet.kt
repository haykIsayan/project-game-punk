package com.example.project_game_punk.features.common.game_progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.example.game_punk_domain.domain.entity.gameProgressStatusItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameProgressModalBottomSheet(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    game: GameEntity,
    onGameProgressSelected: (GameProgressStatus) -> Unit
) {
    val onGameProgressItemTapped: (GameProgressStatus) -> Unit = { gameProgress ->
        onGameProgressSelected.invoke(gameProgress)
        scope.launch {
            state.animateTo(ModalBottomSheetValue.Hidden)
        }
    }

    val gameProgressItem: @Composable (GameProgressStatus) -> Unit = { gameProgressStatus ->
        val color = colorResource(GameProgressMapper.color(gameProgressStatus))
        val textColor = colorResource(GameProgressMapper.displayTextColor(gameProgressStatus))
        val borderColor =  if (gameProgressStatus == GameProgressStatus.notFollowing) {
            Color.White
        } else {
            colorResource(GameProgressMapper.color(gameProgressStatus))
        }
        val text = stringResource(GameProgressMapper.actionText(gameProgressStatus = gameProgressStatus)).uppercase()

        Box(modifier = Modifier
            .clickable {
                onGameProgressItemTapped.invoke(gameProgressStatus)
            }
            .fillMaxWidth()
            .height(50.dp)
            .padding(6.dp)
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

    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        ),
        sheetBackgroundColor = Color.Black,
        sheetContent = {
            Text(
                text = game.name ?: "",
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            LazyColumn {
                items(gameProgressStatusItems(game)) { gameProgress ->
                    gameProgressItem.invoke(gameProgress)
                }
            }
    }) {

    }
}