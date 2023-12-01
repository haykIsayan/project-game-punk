package com.example.project_game_punk.features.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project_game_punk.ui.theme.gamePunkPrimary


@Composable
fun MainBottomNavigation(
    navController: NavController
) {
    val context = LocalContext.current
    val items = MainNavigationTab.Items.items

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        gamePunkPrimary.copy(alpha = 0.8f)
                    ),
                )
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(gamePunkPrimary.copy(alpha = 0.8f)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val entry by navController.currentBackStackEntryAsState()
            val currentRoute = entry?.destination?.route
            items.forEach { item ->
                val isSelected = item.route == currentRoute


                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route)
                              },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .background(gamePunkPrimary.copy(alpha = 0.8f)),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            val entry by navController.currentBackStackEntryAsState()
            val currentRoute = entry?.destination?.route
            items.forEach { item ->
                val isSelected = item.route == currentRoute
                AnimatedVisibility(
                    visible = isSelected,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color.White)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}
