package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.authentication.AuthUiError

@Composable
fun AuthUiError(authUiError: AuthUiError?) {
    when (authUiError) {
        null -> {}
        AuthUiError.FieldsEmpty -> AuthUiErrorText(text = "Please complete all fields")
        AuthUiError.PasswordIncorrect -> AuthUiErrorText(text = "Password is incorrect")
        AuthUiError.EmailAlreadyInUse -> AuthUiErrorText(text = "Email already in use")
        AuthUiError.PasswordsDoNotMatch -> AuthUiErrorText(text = "Passwords don't match")
        else -> AuthUiErrorText(text = "User not found")
    }
}

@Composable
private fun AuthUiErrorText(text: String) {
    Text(
        modifier = Modifier.padding(vertical = 12.dp),
        text = text,
        color = Color.Red,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}