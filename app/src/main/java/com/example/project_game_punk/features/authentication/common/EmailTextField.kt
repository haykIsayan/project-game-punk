package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.project_game_punk.features.authentication.AuthUiModel


@Composable
fun EmailTextField(
    authUiModel: AuthUiModel,
    onValueChanged: (String) -> Unit
) {
    AuthTextField(
        text = authUiModel.userAuthModel.email,
        hint = "Email",
        isEnabled = !authUiModel.isLoading,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                tint = Color.White,
                contentDescription = ""
            )
        }
    ) { text ->
        onValueChanged(text)
    }
}