package com.example.project_game_punk.features.authentication

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.features.authentication.common.*
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper

@Composable
fun SignInScreen(signInViewModel: SignInViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        val state = signInViewModel.getState().observeAsState().value
        Box(modifier = Modifier.align(Alignment.Center)) {
            LoadableStateWrapper(
                state = state,
            ) {
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
                    AuthUiError(it.authUiError)
                    Spacer(modifier = Modifier.height(12.dp))
                    EmailTextField(authUiModel = it) { value ->
                        signInViewModel.updateEmail(value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    PasswordTextField(authUiModel = it) { value ->
                        signInViewModel.updatePassword(value)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
