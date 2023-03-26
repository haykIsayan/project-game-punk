package com.example.project_game_punk.features.common.composables

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.features.game_details.GameDetailsActivity

@Composable
fun GameListItem(
    game: GameEntity,
    trailingButton: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable {
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            AsyncImage(
                modifier = Modifier.size(80.dp, 100.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.backgroundImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Text(
                text = game.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                fontSize = 18.sp
            )
            trailingButton()
    }
}
