package com.example.project_game_punk.features.common.game_progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.entity.gameProgressStatusItems
import com.example.project_game_punk.R
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

    val gameProgressItem: @Composable (GameProgressStatus) -> Unit = { gameProgress ->
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(GameProgressMapper.color(gameProgress)),
            ),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(R.color.white),
            ),
            onClick = {
            onGameProgressItemTapped.invoke(gameProgress)
        }) {
            Text(
                text = stringResource(GameProgressMapper.actionText(gameProgress)),
                color = colorResource(GameProgressMapper.displayTextColor(gameProgress)),
                fontWeight = FontWeight.Bold
            )
        }
    }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(10.dp),
        sheetBackgroundColor = Color.Black,
        sheetContent = {
            Text(
                text = game.name ?: "",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
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