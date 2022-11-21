package com.example.project_game_punk.features.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.DiscoverScreen
import com.example.project_game_punk.features.profile.ProfileScreen
import com.example.project_game_punk.features.search.SearchScreen

@Composable
fun NavigationComponent(
    navController: NavHostController,
    sheetController: GameProgressBottomSheetController
) {
    NavHost(
        modifier = Modifier.padding(bottom = 60.dp),
        navController = navController,
        startDestination = MainNavigationTab.DiscoverMainNavigationTab.route
    ) {
        composable(route = MainNavigationTab.DiscoverMainNavigationTab.route) {
            DiscoverScreen(
                nowPlayingViewModel = hiltViewModel(),
                featuredGameViewModel = hiltViewModel(),
                trendingGamesViewModel = hiltViewModel(),
                recommendedGameViewModel = hiltViewModel(),
                navController = navController,
                sheetController = sheetController
            )
        }
        composable(route = MainNavigationTab.ProfileMainNavigationTab.route) {
            ProfileScreen(searchViewModel = hiltViewModel())
        }

        composable(route = MainNavigationTab.SearchMainNavigationTab.route) {
            SearchScreen(
                searchResultsViewModel = hiltViewModel(),
                sheetController = sheetController
            )
        }
    }
}