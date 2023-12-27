package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.project_game_punk.features.authentication.AuthUiModel

@Composable
fun AuthSecondaryButton(
    text: String,
    authUiModel: AuthUiModel,
    onPressed: () -> Unit
) {
    Text(
        modifier = Modifier
            .clickable { if (!authUiModel.isLoading) { onPressed.invoke() } },
        text = /*"Don't have an account? Sign Up"*/text,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

