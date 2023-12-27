package com.example.project_game_punk.features.authentication.common

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.project_game_punk.features.authentication.AuthUiModel


@Composable
fun DisplayNameTextField(
    authUiModel: AuthUiModel,
    onValueChanged: (String) -> Unit
) {
    AuthTextField(
        text = authUiModel.userAuthModel.displayName,
        hint = "Display Name",
        isEnabled = !authUiModel.isLoading,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                tint = Color.White,
                contentDescription = ""
            )
        },
    ) { text ->
        onValueChanged(text)
    }
}