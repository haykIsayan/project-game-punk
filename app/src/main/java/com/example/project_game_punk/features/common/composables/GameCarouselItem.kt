package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    onProgressSelected: (GameModel, GameProgress) -> Unit
) {
    Box(modifier = Modifier
        .size(280.dp, 180.dp)
        .padding(6.dp)
        .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.backgroundImage)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        if (game.name != null) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    ) .fillMaxWidth().align(Alignment.BottomCenter)
//                modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
//                .background(color = Color.Black.copy(alpha = 0.5F))
            ) {
                Text(
                    text = game.name,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                )
            }
        }
        if (sheetController != null) {
            GameProgressButton(
                game = game,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                onProgressSelected = onProgressSelected,
                controller = sheetController
            )
        }
    }
}
