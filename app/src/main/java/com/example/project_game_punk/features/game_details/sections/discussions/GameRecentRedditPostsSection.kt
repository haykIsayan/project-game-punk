package com.example.project_game_punk.features.game_details.sections.discussions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.game_punk_domain.domain.entity.GameRedditPostEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid

@Composable
fun GameRecentRedditPostsSection(
    gameRecentRedditPostsViewModel: GameRecentRedditPostsViewModel
) {
    val state = gameRecentRedditPostsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { message ->
            Text(text = "MAUFAKA : $message")
        }
    ) { gameRedditPosts ->
        GameRecentRedditPostsSectionLoadedState(gameRedditPosts)
    }
}

@Composable
private fun GameRecentRedditPostsSectionLoadedState(
    gameRedditPosts: List<GameRedditPostEntity>
) {
    if (gameRedditPosts.isEmpty()) return
    Column {
        SectionTitle(title = "Recent posts from Reddit")
        ItemCarousel(
            itemDecorator = ItemCarouselDecorators.pillItemDecorator,
            items = gameRedditPosts
        ) { gameRedditPost ->
            GameRecentRedditPostItem(gameRedditPost)
        }
    }
}

@Composable
private fun GameRecentRedditPostItem(gameRedditPost: GameRedditPostEntity) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = gameRedditPost.redditUsername,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 10.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = gameRedditPost.name,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 12.dp
            ),
            text = gameRedditPost.text,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}