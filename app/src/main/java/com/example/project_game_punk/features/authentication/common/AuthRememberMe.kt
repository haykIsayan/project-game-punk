package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.project_game_punk.features.authentication.AuthUiModel

@Composable
fun AuthRememberMe(authUiModel: AuthUiModel, onRememberMeChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = authUiModel.rememberMe,
            onCheckedChange = onRememberMeChanged,
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.Black,
                checkedColor = Color.White,
                uncheckedColor = Color.White.copy(alpha = 0.5f)
            )
        )
        Text(
            text = "Remember me",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }


}