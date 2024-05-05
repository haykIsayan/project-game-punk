package com.example.project_game_punk.features.common.composables.carousels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ItemCarouselPill(
    text: String,
    color: Color = Color.Transparent,
    onTap: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 24.dp)
            .clickable { onTap.invoke() }
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .width(100.dp)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}