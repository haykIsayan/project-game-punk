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
import androidx.compose.ui.unit.dp


@Composable
fun ItemCarouselPill(
    text: String,
    color: Color = Color.Transparent,
    displayCross: Boolean = false,
    onTap: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(color)
            .clickable { onTap.invoke() }
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (displayCross) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
                Box(modifier = Modifier.width(5.dp))
                Text(
                    text = text,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        } else {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}