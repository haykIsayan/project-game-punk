package com.example.project_game_punk.features.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_punk_grid.GamePunkGridScreen
import com.example.project_game_punk.features.discover.DiscoverScreen
import com.example.project_game_punk.features.discover.trending.TrendingGamesViewModel
import com.example.project_game_punk.features.game_collection.game_collection_details.GameCollectionDetailsScreen
import com.example.project_game_punk.features.game_details.GameDetailsScreen
import com.example.project_game_punk.features.profile.ProfileFollowersViewModel
import com.example.project_game_punk.features.profile.ProfileFollowingViewModel
import com.example.project_game_punk.features.profile.ProfileLibraryViewModel
import com.example.project_game_punk.features.profile.ProfileScreen
import com.example.project_game_punk.features.profile.ProfileUsersScreen
import com.example.project_game_punk.features.profile.favorite_games.FavoriteGamesViewModel
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
                countdownViewModel = hiltViewModel(),
                recentFollowingReviewsViewModel = hiltViewModel(),
                featuredGameViewModel = hiltViewModel(),
                updatesAndPatchesViewModel = hiltViewModel(),
                trendingGamesViewModel = hiltViewModel(),
                recentGamesViewModel = hiltViewModel(),
                gamingNewsViewModel = hiltViewModel(),
                upcomingGamesViewModel = hiltViewModel(),
                sheetController = sheetController
            )
        }
        composable(route = MainNavigationTab.ProfileMainNavigationTab.route) {


//            it.arguments.g

            ProfileScreen(
                nowPlayingViewModel = hiltViewModel(),
                profileArtworksViewModel = hiltViewModel(),
                profileUserViewModel = hiltViewModel(),
                profileLibraryViewModel = hiltViewModel(),
                favoriteGamesViewModel = hiltViewModel(),
                profileGameCollectionsViewModel = hiltViewModel(),
                controller = sheetController
            )
        }

        composable(route = MainNavigationTab.SearchMainNavigationTab.route) {
            SearchScreen(
                searchFiltersViewModel = hiltViewModel(),
                searchGamesViewModel = hiltViewModel(),
                searchUsersViewModel = hiltViewModel(),
                sheetController = sheetController
            )
        }

        composable(route = "game_collection/{userId}/{collectionId}") {
            val userId = it.arguments?.getString("userId")
            val collectionId = it.arguments?.getString("collectionId")
            GameCollectionDetailsScreen(
                gameCollectionId = collectionId,
                profileLibraryViewModel = hiltViewModel(),
                gameCollectionDetailsViewModel = hiltViewModel()
            )
        }

        composable(route = "game/{gameId}") {
            val gameId = it.arguments?.getString("gameId")
            GameDetailsScreen(
                gameId = gameId,
                gameDetailsViewModel = hiltViewModel(),
                gameAgeRatingViewModel = hiltViewModel(),
                gameDeveloperPublisherViewModel = hiltViewModel(),
                gameReleaseDateViewModel = hiltViewModel(),
                gameStoresViewModel = hiltViewModel(),
                gameDetailsNewsViewModel = hiltViewModel(),
                gamePlatformsViewModel = hiltViewModel(),
                gameGenresViewModel = hiltViewModel(),
                gameScreenshotsViewModel = hiltViewModel(),
                gameDLCsViewModel = hiltViewModel(),
                gameDetailsSimilarGamesViewModel = hiltViewModel(),
                gameUserReviewViewModel = hiltViewModel(),
                gameFollowingUserReviewsViewModel = hiltViewModel(),
                gameAchievementsViewModel = hiltViewModel(),
                gameRecentRedditPostsViewModel = hiltViewModel()
            ) {

            }
        }

        composable(route = "trending_games") {
            GamePunkGridScreen(
                title = "Trending Games",
                stateViewModel = hiltViewModel<TrendingGamesViewModel>()
            )
        }

        composable(route = "library") {
            GamePunkGridScreen(
                title = "Library",
                stateViewModel = hiltViewModel<ProfileLibraryViewModel>()
            )
        }

        composable(route = "favorite_games") {
            GamePunkGridScreen(
                title = "Favorite Games",
                stateViewModel = hiltViewModel<FavoriteGamesViewModel>()
            )
        }

        composable(route = "user/{userId}") {
            val userId = it.arguments?.getString("userId")
            ProfileScreen(
                userId = userId,
                nowPlayingViewModel = hiltViewModel(),
                profileUserViewModel = hiltViewModel(),
                profileArtworksViewModel = hiltViewModel(),
                profileGameCollectionsViewModel = hiltViewModel(),
                favoriteGamesViewModel = hiltViewModel(),
                profileLibraryViewModel = hiltViewModel(),
                controller = sheetController
            )
        }

        composable(route = "following/{userId}") {
            val userId = it.arguments?.getString("userId")

            ProfileUsersScreen(
                userId = userId,
                title = "Following",
                stateViewModel = hiltViewModel<ProfileFollowingViewModel>()
            )
        }

        composable(route = "followers/{userId}") {
            val userId = it.arguments?.getString("userId")
            ProfileUsersScreen(
                userId = userId,
                title = "Followers",
                stateViewModel = hiltViewModel<ProfileFollowersViewModel>()
            )
        }
    }
}