package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        AuthUiError.PasswordIncorrect -> AuthUiErrorText(text = "Password you entered is incorrect")
        AuthUiError.EmailAlreadyInUse -> AuthUiErrorText(text = "Email you entered is already in use")
        AuthUiError.PasswordWeak -> AuthUiErrorText(text = "Password you entered is too weak")
        AuthUiError.BadEmailFormat -> AuthUiErrorText(text = "Please enter a valid email address")
        AuthUiError.PasswordsDoNotMatch -> AuthUiErrorText(text = "Passwords you entered don't match")
        else -> AuthUiErrorText(text = "We couldn't find you")
    }
}

@Composable
private fun AuthUiErrorText(text: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = "",
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = text,
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}