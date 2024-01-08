package com.example.project_game_punk.features.authentication

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.R
import com.example.project_game_punk.features.authentication.common.AuthPrimaryButton
import com.example.project_game_punk.features.authentication.common.AuthSecondaryButton
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.main.MainActivity
import com.example.project_game_punk.ui.theme.cyberPunk
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
) {
    Scaffold(
        backgroundColor = Color.Transparent,
        topBar = {
            val state = authViewModel.getState().observeAsState().value
            LoadableStateWrapper(
                state = state,
            ) { authMode ->
                if (authMode != AuthMode.ActiveUserSession) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        text = "GamePunk",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cyberPunk,
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }
            }
        },
        bottomBar = {
            AuthButton(
                authViewModel = authViewModel,
                signInViewModel = signInViewModel,
                signUpViewModel = signUpViewModel
            )
        }
    ) {
        val padding = it
        val context = LocalContext.current
        val state = authViewModel.getState().observeAsState().value
        LoadableStateWrapper(
            state = state,
            loadingState = {
                AuthLoadingState()
            }
        ) { authMode ->
            when (authMode) {
                AuthMode.SignIn -> SignInScreen(signInViewModel = signInViewModel)
                AuthMode.SignUp -> SignUpScreen(signUpViewModel = signUpViewModel)
                AuthMode.ActiveUserSession -> {
                    LaunchedEffect(Unit) {
                        context.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            ).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            }
                        )
                    }
                }
            }
        }
    }
}



@Composable
private fun AuthLoadingState() {
    val visible = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            delay(1000)
            visible.value = true
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "G",
                fontWeight = FontWeight.Bold,
                fontFamily = cyberPunk,
                color = Color.White,
                fontSize = 50.sp
            )
            Text(
                modifier = Modifier
                    .animateContentSize()
                    .size(if (visible.value) Int.MAX_VALUE.dp else 0.dp),
                textAlign = TextAlign.Center,
                text = "ame",
                fontWeight = FontWeight.Bold,
                fontFamily = cyberPunk,
                color = Color.White,
                fontSize = 50.sp
            )
            Text(
                textAlign = TextAlign.Center,
                text = "P",
                fontWeight = FontWeight.Bold,
                fontFamily = cyberPunk,
                color = Color.White,
                fontSize = 50.sp
            )
            Text(
                modifier = Modifier
                    .animateContentSize()
                    .size(if (visible.value) Int.MAX_VALUE.dp else 0.dp),
                textAlign = TextAlign.Center,
                text = "unk",
                fontWeight = FontWeight.Bold,
                fontFamily = cyberPunk,
                color = Color.White,
                fontSize = 50.sp
            )
        }
    }
}

@Composable
private fun AuthButton(
    authViewModel: AuthViewModel,
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            val state = authViewModel.getState().observeAsState().value
            LoadableStateWrapper(state = state) { authMode ->
                when (authMode) {
                    AuthMode.SignIn -> GoToSignUpButton(
                        authViewModel = authViewModel,
                        signInViewModel = signInViewModel
                    )
                    AuthMode.SignUp -> GoToSignInButton(
                        authViewModel = authViewModel,
                        signUpViewModel = signUpViewModel
                    )
                    else -> {}
                }
            }
        }
        Box(modifier = Modifier.padding(12.dp)) {
            val state = authViewModel.getState().observeAsState().value
            LoadableStateWrapper(state = state) { authMode ->
                when (authMode) {
                    AuthMode.SignIn -> SignInButton(signInViewModel = signInViewModel)
                    AuthMode.SignUp -> SignUpButton(signUpViewModel = signUpViewModel)
                    else -> {}
                }
            }
        }
    }




}

@Composable
private fun GoToSignInButton(
    authViewModel: AuthViewModel,
    signUpViewModel: SignUpViewModel
) {
    val state = signUpViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { authUiModel ->
        AuthSecondaryButton(
            text = "Already have an account? Sign In",
            authUiModel = authUiModel
        ) {
            authViewModel.setToSignInMode()
        }
    }
}

@Composable
private fun GoToSignUpButton(
    authViewModel: AuthViewModel,
    signInViewModel: SignInViewModel
) {
    val state = signInViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { authUiModel ->
        AuthSecondaryButton(
            text = "Don't have an account? Sign Up",
            authUiModel = authUiModel
        ) {
            authViewModel.setToSignUpMode()
        }
    }
}

@Composable
private fun SignInButton(signInViewModel: SignInViewModel) {
    val context = LocalContext.current
    val state = signInViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { authUiModel ->
        AuthPrimaryButton(
            text = "Sign In",
            authUiModel = authUiModel
        ) {
            signInViewModel.signIn { user ->
                user.id?.let { userId ->
                        context.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            ).apply {
                                putExtra(
                                    MainActivity.USER_ID_INTENT_EXTRA,
                                    userId
                                )
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            }
                        )
                }
            }
        }
    }
}

@Composable
private fun SignUpButton(signUpViewModel: SignUpViewModel) {
    val context = LocalContext.current
    val state = signUpViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { authUiModel ->
        AuthPrimaryButton(
            text = "Sign Up",
            authUiModel = authUiModel
        ) {
            signUpViewModel.signUp { user ->
                user.id?.let { userId ->
                        context.startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            ).apply {
                                putExtra(
                                    MainActivity.USER_ID_INTENT_EXTRA,
                                    userId
                                )
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            }
                        )
                }
            }
        }
    }
}

@Composable
fun GamePunkIcon() {
    Image(
        modifier = Modifier
            .clip(CircleShape)
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = CircleShape
            ),
        painter = painterResource(
            id = R.mipmap.ic_game_punk_v2_foreground
        ),
        contentDescription = ""
    )
}


