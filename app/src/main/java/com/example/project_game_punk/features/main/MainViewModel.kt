package com.example.project_game_punk.features.main

import androidx.lifecycle.ViewModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.authentication.AuthMode
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState

class MainViewModel(
    private val userCache: UserCache
) : StateViewModel<String, Unit>() {
    override suspend fun loadData(param: Unit?) = ""

    fun setUserId(userId: String) = emit(ViewModelState.SuccessState(userId))
}