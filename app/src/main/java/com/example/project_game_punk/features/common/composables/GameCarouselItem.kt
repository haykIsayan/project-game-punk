package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton

@Composable
fun GameCarouselItem(
    game: GameModel,
    sheetController: GameProgressBottomSheetController?,
    trailing: @Composable () -> Unit = {},
    onProgressSelected: (GameModel, GameProgress) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(220.dp, 160.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.7f),
                                Color.Transparent
                            )
                        )
                    )
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.backgroundImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            }
            if (game.name != null) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(

                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = game.name,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }
            trailing.invoke()
        }
        if (sheetController != null) {
            GameProgressButton(
                game = game,
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                onProgressSelected = onProgressSelected,
                controller = sheetController
            )
        }
    }
}
