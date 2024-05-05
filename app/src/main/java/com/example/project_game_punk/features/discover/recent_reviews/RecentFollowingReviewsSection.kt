package com.example.project_game_punk.features.discover.recent_reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.GameUserScoreDisplay
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorator
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.main.GamePunkNavigator


@Composable
fun RecentFollowingReviewsSection(
    recentFollowingReviewsViewModel: RecentFollowingReviewsViewModel
) {
    val state = recentFollowingReviewsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { followerReviews ->
        RecentFollowingReviewsSectionLoadedState(
            followerReviews = followerReviews
        )
    }
}

@Composable
private fun RecentFollowingReviewsSectionLoadedState(
    followerReviews: List<GameUserReviewsState>
) {
   Column {
       SectionTitle(title = "Reviews from people you follow")
       ItemCarousel(
           items = followerReviews,
           itemDecorator = ItemCarouselDecorators.pillItemDecorator
       ) { gameUserReviewsState ->
           val user = gameUserReviewsState.user ?: return@ItemCarousel
           val gameReview = gameUserReviewsState.review ?: return@ItemCarousel
           GameReviewItem(
               user = user,
               gameReview = gameReview
           )
       }
   }
}

@Composable
private fun GameReviewItem(
    user: UserEntity,
    gameReview: GameReviewEntity
) {
    Row {
        Column(
            Modifier
                .width(220.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            Row(
                Modifier.padding(12.dp)
            ) {
                Box(modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .clickable {
                        GamePunkNavigator.navigate("profile/${user.id}")
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(25.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    Modifier.height(50.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    user.displayName?.let { displayName ->
                        Text(
                            modifier = Modifier.padding(vertical = 5.dp),
                            text = displayName,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Box(Modifier.padding(vertical = 3.dp)) {
//                    GameUserScoreDisplay(game = game)
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                text = gameReview.userReview,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }

//    GameCarouselItem(game = gam)

}


