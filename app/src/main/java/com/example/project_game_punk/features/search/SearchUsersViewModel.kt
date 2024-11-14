package com.example.project_game_punk.features.search

import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.SearchUsersInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchUsersViewModel @Inject constructor(
    private val searchUsersInteractor: SearchUsersInteractor
): StateViewModel<List<UserEntity>, String>() {


    fun searchUsers(query: String) {
        loadState(query, force = true)
    }

    override suspend fun loadData(param: String?): List<UserEntity> {
        param ?: return emptyList()
        return searchUsersInteractor.execute(param)
    }

}