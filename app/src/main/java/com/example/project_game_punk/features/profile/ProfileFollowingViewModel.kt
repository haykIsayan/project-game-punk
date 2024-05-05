package com.example.project_game_punk.features.profile

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.GetUserFollowingInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFollowingViewModel @Inject constructor(
    private val getUserFollowingInteractor: GetUserFollowingInteractor
): StateViewModel<List<UserEntity>, String>() {

    override suspend fun loadData(param: String?): List<UserEntity> {
        val userId = param ?: return emptyList()
        return getUserFollowingInteractor.execute(userId)
    }

}