package com.example.project_game_punk.features.discover

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.news.GameNewsSection
import com.example.project_game_punk.features.discover.news.GameNewsViewModel
import com.example.project_game_punk.features.discover.playing.NowPlayingSection
import com.example.project_game_punk.features.discover.playing.NowPlayingViewModel
import com.example.project_game_punk.features.discover.recent.RecentGamesViewModel
import com.example.project_game_punk.features.discover.recommended.RecommendedGamesSection
import com.example.project_game_punk.features.discover.recommended.RecommendedGamesViewModel
import com.example.project_game_punk.features.discover.trending.TrendingGamesSection
import com.example.project_game_punk.features.discover.trending.TrendingGamesViewModel
import com.example.project_game_punk.features.main.MainNavigationTab

@Composable
fun DiscoverScreen(
    gameNewsViewModel: GameNewsViewModel? = null,
    nowPlayingViewModel: NowPlayingViewModel? = null,
    trendingGamesViewModel: TrendingGamesViewModel? = null,
    recentGamesViewModel: RecentGamesViewModel? = null,
    recommendedGamesViewModel: RecommendedGamesViewModel? = null,
    navController: NavHostController,
    sheetController: GameProgressBottomSheetController
) {
    LazyColumn {
        
//        gameNewsViewModel?.let {
//            item {
//                GameNewsSection(gameNewsViewModel = it)
//            }
//        }

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
//        if (recentGamesViewModel != null) {
//            item {
//                RecentGamesSection(
//                    recentGamesViewModel,
//                    sheetController,
//                )
//            }
//        }
        recommendedGamesViewModel?.let {
            item {
                RecommendedGamesSection(
                    recommendedGamesViewModel,
                    sheetController
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SearchCta(navController: NavHostController) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(6.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.White,
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
        ),
        onClick = {
            navController.navigate(MainNavigationTab.SearchMainNavigationTab.route)
        }) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = Color.White
        )
        Text(text = "Search Games", color = Color.White)
    }
}
