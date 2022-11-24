package com.example.project_game_punk.features.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project_game_punk.R
import com.example.project_game_punk.ui.theme.Purple500


@Composable
fun MainBottomNavigation(
    navController: NavController
) {
    val items = MainNavigationTab.Items.items
    Column {
        BottomNavigation(backgroundColor = Color.Black, modifier = Modifier.height(60.dp)) {
            val entry by navController.currentBackStackEntryAsState()
            val currentRoute = entry?.destination?.route
            items.forEach { item ->
                val isSelected = item.route == currentRoute
                BottomNavigationItem(
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) },
                    label = null,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            tint = if (isSelected) Purple500 else Color.White,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}
