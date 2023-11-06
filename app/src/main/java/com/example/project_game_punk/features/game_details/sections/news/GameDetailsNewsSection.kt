package com.example.project_game_punk.features.game_details.sections.news

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameNewsEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel

@Composable
fun GameDetailsNewsSection(gameNewsViewModel: GameDetailsNewsViewModel) {
    val state = gameNewsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { GameDetailsNewsSectionFailedState(it) },
        loadingState = { GameDetailsNewsSectionLoadingState() }
    ) { gameNewsList ->
        GameDetailsNewsSectionLoadedState(gameNewsList)
    }
}

@Composable
private fun GameDetailsNewsSectionFailedState(errorMessage: String) {

}

@Composable
private fun GameDetailsNewsSectionLoadingState() {
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(Color.DarkGray.copy(alpha = 0.3f))
        .height(40.dp)
    )
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
    Box(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Text(
            text = gameNews.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.Center)
                .border(
                    1.dp,
                    SolidColor(Color.White),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(8.dp),
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
