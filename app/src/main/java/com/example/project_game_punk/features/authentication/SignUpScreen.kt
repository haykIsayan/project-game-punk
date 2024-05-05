package com.example.project_game_punk.features.authentication

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.authentication.common.*
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val state = signUpViewModel.getState().observeAsState().value
        Box(modifier = Modifier.align(Alignment.Center)) {
            LoadableStateWrapper(state = state) { authUiModel ->
                Column(
                    modifier = Modifier
                        .padding(
                            bottom = 164.dp,
                            start = 12.dp,
                            end = 12.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    AuthUiError(authUiModel.authUiError)
                    Spacer(modifier = Modifier.height(12.dp))
                    DisplayNameTextField(authUiModel = authUiModel) { value ->
                        signUpViewModel.updateDisplayName(value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    EmailTextField(authUiModel = authUiModel) { value ->
                        signUpViewModel.updateEmail(value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    PasswordTextField(authUiModel = authUiModel) { value ->
                        signUpViewModel.updatePassword(value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    ConfirmPasswordTextField(authUiModel = authUiModel) { value ->
                        signUpViewModel.updateConfirmPassword(value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

