package com.example.project_game_punk.features.discover.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.discover.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState

@Composable
fun GameNewsSection(gameNewsViewModel: GameNewsViewModel) {
    val state = gameNewsViewModel.getState().observeAsState().value
    Column {
        SectionTitle(title = "Game Updates")
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage -> DiscoverGameFailState(errorMessage) { gameNewsViewModel.loadState(param = "500") } },
            loadingState = { DiscoverGameCarouselLoading() },
        ) { gameNewsState ->

            ItemPagerCarousel(gameNewsState.gameNews) { gameNews ->
            GameNewsCarouselItem(gameNews = gameNews, gameNewsState.game)
        }

        }
    }
}

@Composable
fun GameNewsCarouselItem(gameNews: GameNewsEntity, game: GameEntity) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(220.dp)
        .padding(6.dp)
        .clip(RoundedCornerShape(10.dp))
    ) {
        Row {
            Box(modifier = Modifier.size(150.dp, 220.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.backgroundImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                )
            }
            Text(
                text = gameNews.title,
                modifier = Modifier.fillMaxWidth().height(220.dp).padding(8.dp),
                        fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
            )
        }
    }
}