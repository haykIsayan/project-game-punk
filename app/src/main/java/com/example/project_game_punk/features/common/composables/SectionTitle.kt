package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(title: String, isLoading: Boolean = false) {
    val showShimmer = remember {
        mutableStateOf(isLoading)
    }
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = if (isLoading) Color.Transparent else Color.White,
        modifier = if (isLoading) {
            Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
        } else {
            Modifier.padding(12.dp)
        }
    )
}