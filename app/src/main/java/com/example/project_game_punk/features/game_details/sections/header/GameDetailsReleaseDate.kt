package com.example.project_game_punk.features.game_details.sections.header

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GameDetailsReleaseDate() {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp),
        text = "June 27, 2023".uppercase(),
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}