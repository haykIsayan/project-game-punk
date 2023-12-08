package com.example.project_game_punk.features.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.news.GameNewsSection
import com.example.project_game_punk.features.discover.news.GameNewsViewModel
import com.example.project_game_punk.features.discover.playing.NowPlayingSection
import com.example.project_game_punk.features.discover.playing.NowPlayingViewModel
import com.example.project_game_punk.features.discover.recent.RecentGamesSection
import com.example.project_game_punk.features.discover.recent.RecentGamesViewModel
import com.example.project_game_punk.features.discover.recommended.RecommendedGamesSection
import com.example.project_game_punk.features.discover.recommended.RecommendedGamesViewModel
import com.example.project_game_punk.features.discover.trending.TrendingGamesSection
import com.example.project_game_punk.features.discover.trending.TrendingGamesViewModel
import com.example.project_game_punk.features.discover.upcoming.UpcomingGamesSection
import com.example.project_game_punk.features.discover.upcoming.UpcomingGamesViewModel
import com.example.project_game_punk.features.main.MainNavigationTab

@Composable
fun DiscoverScreen(
    gameNewsViewModel: GameNewsViewModel? = null,
    nowPlayingViewModel: NowPlayingViewModel? = null,
    trendingGamesViewModel: TrendingGamesViewModel? = null,
    recentGamesViewModel: RecentGamesViewModel? = null,
    upcomingGamesViewModel: UpcomingGamesViewModel? = null,
    recommendedGamesViewModel: RecommendedGamesViewModel? = null,
    navController: NavHostController,
    sheetController: GameProgressBottomSheetController
) {
    LazyColumn {
        
        gameNewsViewModel?.let {
            item {
                GameNewsSection(gameNewsViewModel = it)
            }
        }

        nowPlayingViewModel?.let {
            item {
                NowPlayingSection(nowPlayingViewModel)
            }
        }

        item {
            SearchCta(navController)
        }

        trendingGamesViewModel?.let {
            item {
                TrendingGamesSection(
                    trendingGamesViewModel,
                    sheetController,
                )
            }
        }
        recentGamesViewModel?.let {
            item {
                RecentGamesSection(
                    recentGamesViewModel,
                    sheetController,
                )
            }
        }

        upcomingGamesViewModel?.let {
            item {
                UpcomingGamesSection(
                    viewModel = upcomingGamesViewModel,
                    sheetController = sheetController
                )
            }
        }

        recommendedGamesViewModel?.let {
            item {
                RecommendedGamesSection(
                    recommendedGamesViewModel,
                    sheetController
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SearchCta(navController: NavHostController) {
    Box(modifier = Modifier
        .clickable {
            navController.navigate(MainNavigationTab.SearchMainNavigationTab.route)
        }
        .fillMaxWidth()
        .height(60.dp)
        .padding(6.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(
            1.dp,
            SolidColor(Color.White),
            shape = RoundedCornerShape(10.dp)
        )
        .background(Color.Transparent)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Search Games", color = Color.White,
            fontSize = 14.sp,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}
