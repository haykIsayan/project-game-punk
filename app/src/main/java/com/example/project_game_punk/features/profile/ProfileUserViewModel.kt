package com.example.project_game_punk.features.profile

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.game_punk_domain.domain.interactors.user.UserSignOutInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileUserViewModel @Inject constructor(
    private val userCache: UserCache,
    private val userSignOutInteractor: UserSignOutInteractor
) : StateViewModel<UserEntity?, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): UserEntity? {
        return userCache.loadAndCacheUser()
    }

    fun signOut(
        onUserSignedOut: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    userSignOutInteractor.execute()
                }
                onUserSignedOut()
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}