package com.example.project_game_punk.features.main

import androidx.compose.runtime.Composable
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
        navController = navController,
        startDestination = MainNavigationTab.DiscoverMainNavigationTab.route
    ) {
        composable(route = MainNavigationTab.DiscoverMainNavigationTab.route) {
            DiscoverScreen(
                featuredGameViewModel = hiltViewModel(),
                gameNewsViewModel = hiltViewModel(),
                trendingGamesViewModel = hiltViewModel(),
                recentGamesViewModel = hiltViewModel(),
                sheetController = sheetController
            )
        }
        composable(route = MainNavigationTab.ProfileMainNavigationTab.route) {
            ProfileScreen(
                nowPlayingViewModel = hiltViewModel(),
                profileUserViewModel = hiltViewModel(),
                profileViewModel = hiltViewModel(),
                favoriteGamesViewModel = hiltViewModel(),
                controller = sheetController
            )
        }

        composable(route = MainNavigationTab.SearchMainNavigationTab.route) {
            SearchScreen(
                searchFiltersViewModel = hiltViewModel(),
                searchResultsViewModel = hiltViewModel(),
                sheetController = sheetController
            )
        }
    }
}