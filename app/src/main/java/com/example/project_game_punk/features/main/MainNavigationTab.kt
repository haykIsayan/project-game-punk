package com.example.project_game_punk.features.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.project_game_punk.R

sealed class MainNavigationTab(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    object DiscoverMainNavigationTab: MainNavigationTab(
        R.string.discover_nav_title,
        Icons.Filled.Home,
        "discover"
    )

    object SearchMainNavigationTab: MainNavigationTab(
        R.string.search_cta_text,
        Icons.Outlined.Search,
        "search"
    )

    object ProfileMainNavigationTab: MainNavigationTab(
        R.string.profile_nav_title,
        Icons.Filled.Person,
        "profile"
    )

    object Items {
        val items = listOf(
            DiscoverMainNavigationTab,
            SearchMainNavigationTab,
            ProfileMainNavigationTab
        )
    }
}