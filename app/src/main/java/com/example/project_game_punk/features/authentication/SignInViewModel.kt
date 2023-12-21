package com.example.project_game_punk.features.authentication

import androidx.lifecycle.viewModelScope
import com.example.game_punk_collection_data.data.user.IncorrectPasswordException
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.UserSignInInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userSignInInteractor: UserSignInInteractor
): StateViewModel<AuthUiModel, String>() {

    init {
        loadState()
    }

    override suspend fun loadData(
        param: String?
    ) = AuthUiModel(UserAuthModel.emptyUserAuth())

    fun signIn(
        onUserSignInSuccess: (UserEntity) -> Unit
    ) {
        val signInModel = getData() ?: return
        val userAuthModel = signInModel.userAuthModel
        if (userAuthModel.email.isEmpty() || userAuthModel.password.isEmpty()) {
            emit(ViewModelState.SuccessState(signInModel.copy(authUiError = AuthUiError.FieldsEmpty)))
            return
        }
        emit(ViewModelState.SuccessState(signInModel.copy(
            authUiError = null,
            isLoading = true
        )))
        viewModelScope.launch(Dispatchers.Main) {
            try {
                getData()?.userAuthModel?.let { userAuthModel ->
                    withContext(Dispatchers.IO) {
                        userSignInInteractor.execute(userAuthModel)
                    }
                }?.let { user ->
                    emit(ViewModelState.SuccessState(signInModel.copy(
                        authUiError = null,
                        isLoading = false
                    )))
                    onUserSignInSuccess(user)
                }
            } catch (e: Exception) {
                if (e is IncorrectPasswordException) {
                    emit(ViewModelState.SuccessState(signInModel.copy(
                        authUiError = AuthUiError.PasswordIncorrect,
                        isLoading = false
                    )))
                } else {
                    emit(
                        ViewModelState.SuccessState(
                            signInModel.copy(
                                authUiError = AuthUiError.SignInFailed,
                                isLoading = false
                            )
                        )
                    )
                }
            }
        }
    }

    fun updateEmail(email: String) {
        getData()?.let { signInModel ->
            val newUserAuthModel = signInModel.userAuthModel.copy(email = email)
            val newSignInModel = signInModel.copy(userAuthModel = newUserAuthModel)
            emit(ViewModelState.SuccessState(newSignInModel))
        }
    }

    fun updatePassword(password: String) {
        getData()?.let { signInModel ->
            val newUserAuthModel = signInModel.userAuthModel.copy(password = password)
            val newSignInModel = signInModel.copy(userAuthModel = newUserAuthModel)
            emit(ViewModelState.SuccessState(newSignInModel))
        }
    }

    fun updateRememberMe(rememberMe: Boolean) {
        getData()?.let { signInModel ->
            val newSignInModel = signInModel.copy(rememberMe = rememberMe)
            emit(ViewModelState.SuccessState(newSignInModel))
        }
    }
}

enum class AuthUiError {
    PasswordIncorrect,
    FieldsEmpty,
    SignInFailed,
    EmailAlreadyInUse,
    PasswordsDoNotMatch
}

data class AuthUiModel(
    val userAuthModel: UserAuthModel,
    val confirmedPassword: String = "",
    val rememberMe: Boolean = false,
    val authUiError: AuthUiError? = null,
    val isLoading: Boolean = false
)