package com.example.project_game_punk.features.authentication

import androidx.lifecycle.viewModelScope
import com.example.game_punk_collection_data.data.user.EmailAlreadyInUseException
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.UserSignUpInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userSignUpInteractor: UserSignUpInteractor
): StateViewModel<AuthUiModel, String>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: String?) = AuthUiModel(UserAuthModel.emptyUserAuth())

    fun signUp(
        onUserSignUpSuccess: (UserEntity) -> Unit
    ) {
        val authUiModel = getData() ?: return
        val userAuthModel = authUiModel.userAuthModel

        if (isEmptyFields(userAuthModel)) {
            emit(ViewModelState.SuccessState(
                authUiModel.copy(authUiError = AuthUiError.FieldsEmpty)
            ))
            return
        }
        if (authUiModel.confirmedPassword != userAuthModel.password) {
            emit(ViewModelState.SuccessState(
                authUiModel.copy(authUiError = AuthUiError.PasswordsDoNotMatch)
            ))
            return
        }
        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    userSignUpInteractor.execute(userAuthModel)
                }.let { user ->
                    onUserSignUpSuccess(user)
                }
            } catch (e: Exception) {
                if (e is EmailAlreadyInUseException) {
                    emit(ViewModelState.SuccessState(
                        authUiModel.copy(authUiError = AuthUiError.EmailAlreadyInUse)
                    ))
                } else {
                    emit(ViewModelState.SuccessState(
                        authUiModel.copy(authUiError = AuthUiError.SignInFailed)
                    ))
                }
            }
        }
    }

    private fun isEmptyFields(
        userAuthModel: UserAuthModel
    ) = userAuthModel.email.isEmpty()
                || userAuthModel.password.isEmpty()
                || userAuthModel.displayName.isEmpty()

    fun updateDisplayName(displayName: String) {
        getData()?.let { signInModel ->
            val newUserAuthModel = signInModel.userAuthModel.copy(displayName = displayName)
            val newSignInModel = signInModel.copy(userAuthModel = newUserAuthModel)
            emit(ViewModelState.SuccessState(newSignInModel))
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

    fun updateConfirmPassword(password: String) {
        getData()?.let { authUiModel ->
            val authUiModelWithError = if (password != authUiModel.userAuthModel.password && password.isNotEmpty()) {
                authUiModel.copy(authUiError = AuthUiError.PasswordsDoNotMatch)
            } else {
                authUiModel.copy(authUiError = null)
            }
            val newAuthUiModel = authUiModelWithError.copy(confirmedPassword = password)
            emit(ViewModelState.SuccessState(newAuthUiModel))
        }
    }

    fun updateRememberMe(rememberMe: Boolean) {
        getData()?.let { signInModel ->
            val newSignInModel = signInModel.copy(rememberMe = rememberMe)
            emit(ViewModelState.SuccessState(newSignInModel))
        }
    }

}