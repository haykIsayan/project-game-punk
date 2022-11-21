package com.example.project_game_punk.features.common.game_progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.domain.entity.GameProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameProgressModalBottomSheet(
    state: ModalBottomSheetState,
    scope: CoroutineScope,
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
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Black,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(gameProgress.color),
            ),
            onClick = {
            onGameProgressItemTapped.invoke(gameProgress)
        }) {
            Text(
                text = context.getString(gameProgress.text),
                color = colorResource(gameProgress.color)
            )
        }
    }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(10.dp),
        sheetBackgroundColor = Color.Black,
        sheetContent = {
            LazyColumn {
                items(GameProgress.gameProgressItems()) { gameProgress ->
                    gameProgressItem.invoke(gameProgress)
                }
            }
    }) {

    }
}