package com.example.project_game_punk.features.authentication.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


enum class AuthTrailingMode {
    Clear,
    ShowHide
}

@Composable
fun AuthTextField(
    text: String,
    hint: String,
    isEnabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable () -> Unit = {},
    trailingMode: AuthTrailingMode = AuthTrailingMode.Clear,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: (String) -> Unit
) {
    val show = remember { mutableStateOf(false) }
    TextField(
        text,
        onValueChange = { onTextChanged(it) },
        shape = CircleShape,
        placeholder = {
            Text(
                text = hint,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        },
        enabled = isEnabled,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (
            visualTransformation is PasswordVisualTransformation
            && show.value
            && trailingMode == AuthTrailingMode.ShowHide
        ) {
            VisualTransformation.None
        } else {
            visualTransformation
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = RoundedCornerShape(15.dp)
            ),
        leadingIcon = leadingIcon,
        trailingIcon = {
            when (trailingMode) {
                AuthTrailingMode.Clear -> {
                    if (text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .size(18.dp)
                                .clickable {
                                    onTextChanged("")
                                },
                            imageVector = Icons.Filled.Cancel,
                            tint = Color.White,
                            contentDescription = ""
                        )
                    }
                }
                AuthTrailingMode.ShowHide -> {
                    if (text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .size(18.dp)
                                .clickable {
                                    show.value = !show.value
                                },
                            imageVector = if (show.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            tint = Color.White,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    )
}
