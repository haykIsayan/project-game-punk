package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.project_game_punk.features.authentication.AuthUiModel

@Composable
fun PasswordTextField(
    authUiModel: AuthUiModel,
    onValueChanged: (String) -> Unit
) {
    AuthTextField(
        text = authUiModel.userAuthModel.password,
        hint = "Password",
        isEnabled = !authUiModel.isLoading,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                tint = Color.White,
                contentDescription = ""
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        trailingMode = AuthTrailingMode.ShowHide
    ) { text ->
        onValueChanged(text)
    }
}