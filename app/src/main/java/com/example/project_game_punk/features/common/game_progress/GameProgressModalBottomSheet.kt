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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.models.GameModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameProgressModalBottomSheet(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    game: GameModel,
    controller: GameProgressBottomSheetController,
    onGameProgressSelected: (GameProgress) -> Unit
) {

    val context = LocalContext.current

    val onGameProgressItemTapped: (GameProgress) -> Unit = { gameProgress ->
        onGameProgressSelected.invoke(gameProgress)
        scope.launch {
            state.animateTo(ModalBottomSheetValue.Hidden)
        }
    }

    val gameProgressItem: @Composable (GameProgress) -> Unit = { gameProgress ->
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(gameProgress.color),
            ),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(gameProgress.textColor),
            ),
            onClick = {
            onGameProgressItemTapped.invoke(gameProgress)
        }) {
            Text(
                text = context.getString(gameProgress.actionText),
                color = colorResource(gameProgress.textColor)
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
                items(GameProgress.gameProgressItems(game)) { gameProgress ->
                    gameProgressItem.invoke(gameProgress)
                }
            }
    }) {

    }
}