package com.example.project_game_punk.features.main

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project_game_punk.ui.theme.gamePunkPrimary


@Composable
fun MainBottomNavigation(
    navController: NavController
) {
    val items = MainNavigationTab.Items.items

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(gamePunkPrimary),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val entry by navController.currentBackStackEntryAsState()
            val currentRoute = entry?.destination?.route
            items.forEach { item ->
                val isSelected = item.route == currentRoute
                val ripple = rememberRipple(
                    bounded = false,
                    color = Color.White
                )
                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize()
                        .selectable(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route)
                            },
                            enabled = true,
                            role = Role.Tab,
                            interactionSource = interactionSource,
                            indication = ripple
                        )
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (isSelected)
                            item.selectedIcon
                        else
                            item.unselectedIcon,
                        tint = if (isSelected)
                            Color.White
                        else
                            Color.White.copy(
                                alpha = 0.5f
                            ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
