package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.R
import com.example.project_game_punk.features.authentication.AuthUiModel


@Composable
fun AuthPrimaryButton(
    text: String,
    authUiModel: AuthUiModel,
    onPressed: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!authUiModel.isLoading) {
                    onPressed()
                }
            }
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                SolidColor(colorResource(id = R.color.blue)),
                shape = RoundedCornerShape(10.dp)
            )
            .background(colorResource(id = R.color.blue))
    ) {
        if (authUiModel.isLoading) {
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                color = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        } else {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontSize = 14.sp,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}