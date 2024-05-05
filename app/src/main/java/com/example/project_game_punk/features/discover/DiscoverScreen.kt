@file:OptIn(ExperimentalMaterialApi::class)

package com.example.project_game_punk.features.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.countdown.CountdownSection
import com.example.project_game_punk.features.discover.countdown.CountdownViewModel
import com.example.project_game_punk.features.discover.featured.FeaturedGameSection
import com.example.project_game_punk.features.discover.featured.FeaturedGameViewModel
import com.example.project_game_punk.features.discover.gaming_news.GamingNewsSection
import com.example.project_game_punk.features.discover.gaming_news.GamingNewsViewModel
import com.example.project_game_punk.features.discover.updates_patches.UpdatesAndPatchesSection
import com.example.project_game_punk.features.discover.updates_patches.UpdatesAndPatchesViewModel
import com.example.project_game_punk.features.discover.recent.RecentGamesSection
import com.example.project_game_punk.features.discover.recent.RecentGamesViewModel
import com.example.project_game_punk.features.discover.recent_reviews.RecentFollowingReviewsSection
import com.example.project_game_punk.features.discover.recent_reviews.RecentFollowingReviewsViewModel
import com.example.project_game_punk.features.discover.trending.TrendingGamesSection
import com.example.project_game_punk.features.discover.trending.TrendingGamesViewModel
import com.example.project_game_punk.features.discover.upcoming.UpcomingGamesViewModel
import com.example.project_game_punk.features.game_details.sections.GamePunkTab
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.ui.theme.cyberPunk

@Composable
fun DiscoverScreen(
    countdownViewModel: CountdownViewModel? = null,
    updatesAndPatchesViewModel: UpdatesAndPatchesViewModel? = null,
    recentFollowingReviewsViewModel: RecentFollowingReviewsViewModel?? = null,
    featuredGameViewModel: FeaturedGameViewModel? = null,
    trendingGamesViewModel: TrendingGamesViewModel? = null,
    recentGamesViewModel: RecentGamesViewModel? = null,
    gamingNewsViewModel: GamingNewsViewModel? = null,
    upcomingGamesViewModel: UpcomingGamesViewModel? = null,
    sheetController: GameProgressBottomSheetController
) {
    val index = remember { mutableStateOf(0) }
    val isRefreshing = remember { mutableStateOf(false) }
    val state = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = false
            when (index.value) {
                0 -> {
                    featuredGameViewModel?.loadState(force = true)
                    trendingGamesViewModel?.loadState(force = true)
                    recentGamesViewModel?.loadState(force = true)
                    gamingNewsViewModel?.loadState(force = true)
                }
                1 -> {
                    countdownViewModel?.loadState(force = true)
                    updatesAndPatchesViewModel?.loadState(force = true)
                    recentFollowingReviewsViewModel?.loadState(force = true)
                }
            }
        }
    )

    Box(
        modifier = Modifier.pullRefresh(state)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                GamePunkHeader()
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


            item {
                GamePunkTab(
                    selectedItemIndex = index.value,
                    items = listOf(
                        "Explore",
                        "Dashboard",
                        "Community"

                    ),
                    onClick = {
                        index.value = it
                    }
                )
            }

            when (index.value) {
                0 -> {
                    exploreSection(
                        featuredGameViewModel = featuredGameViewModel,
                        trendingGamesViewModel = trendingGamesViewModel,
                        recentGamesViewModel = recentGamesViewModel,
                        gamingNewsViewModel = gamingNewsViewModel,
                        upcomingGamesViewModel = upcomingGamesViewModel,
                        sheetController = sheetController
                    )
                }
                1 -> {
                    dashboardSection(
                        updatesAndPatchesViewModel = updatesAndPatchesViewModel,
                        countdownViewModel = countdownViewModel,
                        recentFollowingReviewsViewModel = recentFollowingReviewsViewModel
                    )
                }
                2 -> {
                    communitySection()
                }
            }
        }

        if (index.value == 2) {
            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                FloatingActionButton(
                    modifier = Modifier.padding(12.dp),
                    onClick = {

                    },
                    backgroundColor = Color.White.copy(alpha = 0.05f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PostAdd,
                        contentDescription = "",
                        tint = Color.White
                    )

                }
            }
        }


        PullRefreshIndicator(isRefreshing.value, state, Modifier.align(Alignment.TopCenter))
    }
}

private fun LazyListScope.exploreSection(
    featuredGameViewModel: FeaturedGameViewModel?,
    trendingGamesViewModel: TrendingGamesViewModel?,
    recentGamesViewModel: RecentGamesViewModel?,
    gamingNewsViewModel: GamingNewsViewModel?,
    upcomingGamesViewModel: UpcomingGamesViewModel?,
    sheetController: GameProgressBottomSheetController
) {
    featuredGameViewModel?.let {
        item {
            FeaturedGameSection(
                featuredGameViewModel = featuredGameViewModel,
                sheetController = sheetController
            )
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
    gamingNewsViewModel?.let {
        item {
            GamingNewsSection(
                gamingNewsViewModel
            )
        }
    }
}

private fun LazyListScope.dashboardSection(
    countdownViewModel: CountdownViewModel?,
    updatesAndPatchesViewModel: UpdatesAndPatchesViewModel?,
    recentFollowingReviewsViewModel: RecentFollowingReviewsViewModel?,
) {

    countdownViewModel?.let {
        item {
            CountdownSection(countdownViewModel = it)
        }
    }

    updatesAndPatchesViewModel?.let {
        item {
            UpdatesAndPatchesSection(updatesAndPatchesViewModel = it)
        }
    }

    recentFollowingReviewsViewModel?.let {
        item {
            RecentFollowingReviewsSection(recentFollowingReviewsViewModel = it)
        }
    }

    // todo add recommendations
}


private fun LazyListScope.communitySection() {
    item {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            textAlign = TextAlign.Center,
            text = "Community coming soon!",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
        )
    }
}



@Composable
private fun GamePunkHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = "GamePunk",
            fontWeight = FontWeight.Bold,
            fontFamily = cyberPunk,
            color = Color.White,
            fontSize = 28.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable {
                    GamePunkNavigator.navigate("search")
                },
                imageVector = Icons.Filled.Search,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(12.dp))
            ProfileIcon()
        }
    }
}

@Composable
private fun ProfileIcon() {
//    LoadableStateWrapper(
//        state = state,
//        loadingState = { DiscoverGameCarouselLoading() },
//    ) { user ->
        Box(modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable {
                GamePunkNavigator.navigate("profile")
            }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = ""
            )
        }
}

