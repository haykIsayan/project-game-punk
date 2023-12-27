package com.example.project_game_punk.features.discover

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.featured.FeaturedGameSection
import com.example.project_game_punk.features.discover.featured.FeaturedGameViewModel
import com.example.project_game_punk.features.discover.news.GameNewsSection
import com.example.project_game_punk.features.discover.news.GameNewsViewModel
import com.example.project_game_punk.features.discover.recent.RecentGamesSection
import com.example.project_game_punk.features.discover.recent.RecentGamesViewModel
import com.example.project_game_punk.features.discover.trending.TrendingGamesSection
import com.example.project_game_punk.features.discover.trending.TrendingGamesViewModel
import com.example.project_game_punk.ui.theme.cyberPunk

@Composable
fun DiscoverScreen(
    gameNewsViewModel: GameNewsViewModel? = null,
    featuredGameViewModel: FeaturedGameViewModel? = null,
    trendingGamesViewModel: TrendingGamesViewModel? = null,
    recentGamesViewModel: RecentGamesViewModel? = null,
    sheetController: GameProgressBottomSheetController
) {
    LazyColumn {

        item {
            GamePunkTitle()
        }

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )
        }

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .height(12.dp)
            )
        }

        featuredGameViewModel?.let {
            item {
                FeaturedGameSection(
                    featuredGameViewModel = featuredGameViewModel,
                    sheetController = sheetController
                )
            }
        }

        gameNewsViewModel?.let {
            item {
                GameNewsSection(gameNewsViewModel = it)
            }
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
    }
}

@Composable
private fun GamePunkTitle() {
     Text(
         modifier = Modifier.padding(12.dp),
         textAlign = TextAlign.Start,
         text = "GamePunk",
         fontWeight = FontWeight.Bold,
         fontFamily = cyberPunk,
         color = Color.White,
         fontSize = 28.sp
     )
}

