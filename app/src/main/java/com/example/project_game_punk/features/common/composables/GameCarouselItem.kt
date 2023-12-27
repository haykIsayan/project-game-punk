package com.example.project_game_punk.features.common.composables

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.GameDetailsActivity

@Composable
fun GameCarouselItem(
    game: GameEntity,
    sheetController: GameProgressBottomSheetController? = null,
    trailing: @Composable () -> Unit = {},
    onProgressSelected: ((GameEntity, GameProgressStatus) -> Unit)? = null
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.clickable {
            context.startActivity(
                Intent(
                    context,
                    GameDetailsActivity::class.java
                ).apply {
                    putExtra(
                        GameDetailsActivity.GAME_ID_INTENT_EXTRA,
                        game.id
                    )
                }
            )
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(
                110.dp,
                150.dp
            )
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
                    contentScale = ContentScale.FillHeight,
                )
                Box(modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.BottomCenter)
                ) {
                    GameUserScoreDisplay(game = game)
                }
            }
            trailing.invoke()
        }
        if (sheetController != null && onProgressSelected != null) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp))
            GameProgressButton(
                game = game,
                modifier = Modifier.width(110.dp),
                onProgressSelected = onProgressSelected,
                controller = sheetController
            )
        }
    }
}
