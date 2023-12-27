package com.example.project_game_punk.features.game_details.sections.news

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameWebViewActivity

@Composable
fun GameDetailsNewsSection(gameNewsViewModel: GameDetailsNewsViewModel) {
    val state = gameNewsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameDetailsNewsSectionLoadingState() }
    ) { gameNewsList ->
        GameDetailsNewsSectionLoadedState(gameNewsList)
    }
}

@Composable
private fun GameDetailsNewsSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }

    Column {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
                .fillMaxWidth()
                .height(22.dp)
        )
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .height(120.dp)
        )
    }
}

@Composable
private fun GameDetailsNewsSectionLoadedState(gameNewsList: List<GameNewsEntity>) {
    Column {
        SectionTitle(title = "What's New")
        ItemPagerCarousel(
            gameNewsList
        ) { gameNews ->
            GameDetailsNewsSectionItem(gameNews = gameNews)
        }
    }
}

@Composable
private fun GameDetailsNewsSectionItem(gameNews: GameNewsEntity) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
        .padding(horizontal = 12.dp)
        .clickable {
            onGameDetailsNewsItemClicked(context, gameNews)
        }
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${gameNews.date}  •  ${gameNews.author}",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = gameNews.title,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = gameNews.description,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

private fun onGameDetailsNewsItemClicked(context: Context, gameNews: GameNewsEntity) {
    context.startActivity(
        Intent(
            context,
            GameWebViewActivity::class.java
        ).apply {
            putExtra(
                GameWebViewActivity.URL_INTENT_EXTRA,
                gameNews.url
            )
        }
    )
}
