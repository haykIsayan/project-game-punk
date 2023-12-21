package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(
    title: String,
    isLoading: Boolean = false,
    onArrowPressed: (() -> Unit)? = null
) {
    val showShimmer = remember {
        mutableStateOf(isLoading)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = /*22.sp*/ 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (isLoading) Color.Transparent else Color.White,
            modifier = if (isLoading) {
                Modifier
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(shimmerBrush(showShimmer = showShimmer.value))
            } else {
                Modifier.padding(12.dp)
            }
        )
        onArrowPressed?.let {
            IconButton(
                modifier = Modifier.size(50.dp),
                onClick = onArrowPressed
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    "",
                    tint = Color.White
                )
            }
        }
    }
}