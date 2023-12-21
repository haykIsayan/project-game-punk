package com.example.project_game_punk.features.authentication

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


enum class AuthMode {
    ActiveUserSession,
    SignIn,
    SignUp
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userCache: UserCache,
    private val trackedGamesCache: TrackedGamesCache,
): StateViewModel<AuthMode, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): AuthMode {
        return if (userCache.isActiveUserSession()) AuthMode.ActiveUserSession
        else AuthMode.SignIn
    }

    fun setToSignInMode() = updateData(AuthMode.SignIn)

    fun setToSignUpMode() = updateData(AuthMode.SignUp)

    fun loadUser(userId: String, onUserLoaded: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    userCache.setUserId(userId)
                    userCache.loadAndCacheUser()
                    trackedGamesCache.getMainGameCollection()
                }
                onUserLoaded(userId)
            } catch (e: Exception) {
               println(e)
            }
        }
    }

}