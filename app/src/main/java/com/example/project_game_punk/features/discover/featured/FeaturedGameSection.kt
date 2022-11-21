package com.example.project_game_punk.features.discover.featured

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton

@Composable
fun FeaturedGameSection(
    featuredGameViewModel: FeaturedGameViewModel,
    sheetController: GameProgressBottomSheetController
) {
    val  state = featuredGameViewModel.getState().observeAsState().value

    Column {
        SectionTitle(title = "Featured game")
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage ->
                DiscoverGameFailState(text = errorMessage) {
                    featuredGameViewModel.loadState()
                }
            }, loadingState = { FeaturedGameLoadingState() },
        ) { game ->
            FeatureGameLoadedState(
                game = game,
                trailing =
                {
                    GameProgressButton(
                        game = game,
                        modifier = it.padding(8.dp),
                        controller = sheetController,
                        onProgressSelected = { game, gameProgress ->

                        },
                    )
                }
            )
        }
    }
}

@Composable
private fun FeatureGameLoadedState(
    game: GameModel,
    trailing: @Composable (Modifier) -> Unit,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(15.dp)
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.backgroundImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
            )
            trailing.invoke(Modifier.align(Alignment.TopEnd))
            if (game.name != null) {
                Box(modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
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
        }
    }
}

@Composable
private fun FeaturedGameLoadingState() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(6.dp)) {
        CircularProgressIndicator()
    }
}