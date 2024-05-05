package com.example.project_game_punk.features.game_details.sections.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid

@Composable
fun GameAchievementsSection(
    gameAchievementsViewModel: GameAchievementsViewModel
) {
    val state = gameAchievementsViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state,
        {
            Text(text = "BRUUUUUUUH LOADING")
        }
    ) { achievementStates ->
        GameAchievementsSectionLoadedState(achievementStates = achievementStates)
    }
}

@Composable
private fun GameAchievementsSectionLoadedState(achievementStates: List<GameAchievementState>) {
    if (achievementStates.isEmpty()) return
    Column {
        SectionTitle(title = "Achievements")
        GamePunkGrid(
            modifier = Modifier
                .height(220.dp)
                .padding(horizontal = 6.dp),
            isVertical = false,
            span = 3,
            items = achievementStates
        ) { achievementState ->
            GameAchievementItem(achievementState)
        }
    }
}

@Composable
private fun GameAchievementItem(gameAchievementState: GameAchievementState) {
    Row(
        modifier = Modifier
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(gameAchievementState.gameAchievement.image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier




                .width(180.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
//                .background(Color.White.copy(alpha = 0.05f))

                .border(
                    1.dp,
                    SolidColor(Color.White.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (gameAchievementState.isCompleted)
                        Color.White
                    else
                        Color.Transparent
                )
            ,
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                text = gameAchievementState.gameAchievement.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
            )
        }
    }
}