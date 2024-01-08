package com.example.project_game_punk.features.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.project_game_punk.R

sealed class MainNavigationTab(
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    object DiscoverMainNavigationTab: MainNavigationTab(
        title = R.string.discover_nav_title,
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard,
        route = "discover"
    )

    object SearchMainNavigationTab: MainNavigationTab(
        title = R.string.search_cta_text,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = "search"
    )

    object ProfileMainNavigationTab: MainNavigationTab(
        title = R.string.profile_nav_title,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = "profile"
    )

    object Items {
        val items = listOf(
            DiscoverMainNavigationTab,
            SearchMainNavigationTab,
            ProfileMainNavigationTab
        )
    }
}