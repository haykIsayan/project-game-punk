package com.example.project_game_punk.features.profile

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.user.FollowUserInteractor
import com.example.game_punk_domain.domain.interactors.user.GetUserInteractor
import com.example.game_punk_domain.domain.interactors.user.UnfollowUserInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.game_punk_domain.domain.interactors.user.UserSignOutInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileUserViewModel @Inject constructor(
    private val userCache: UserCache,
    private val getUserInteractor: GetUserInteractor,
    private val followUserInteractor: FollowUserInteractor,
    private val unfollowUserInteractor: UnfollowUserInteractor,
    private val userSignOutInteractor: UserSignOutInteractor
) : StateViewModel<UserEntity?, String>() {

    fun isCurrentUser(userId: String?) = userId == null || userCache.userId() == userId

    fun isFollowing(user: UserEntity) = user.followers?.contains(userCache.userId()) ?: false

    override suspend fun loadData(param: String?): UserEntity? {
        return param?.let { userId ->
            getUserInteractor.execute(userId)
        } ?: userCache.loadAndCacheUser()
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


    fun followUnfollow(user: UserEntity) {
        if (isFollowing(user)) {
            unfollow()
        } else follow()
    }

    fun follow() {
        val userToFollow = getData()
        val currentUserId = userCache.userId() ?: return
        val updatedFollowers =
            userToFollow?.followers?.toMutableList()?.apply { add(currentUserId) } ?: return
        val updatedUserToFollow = userToFollow.withFollowers(updatedFollowers)
        executeIO(
            Dispatchers.IO,
            onBefore = { emit(ViewModelState.SuccessState(updatedUserToFollow)) },
            execute = {
                followUserInteractor.execute(
                    currentUserId, updatedUserToFollow.id ?: ""
                )
            },
            onFail = { emit(ViewModelState.SuccessState(userToFollow)) },
        )
    }

    fun unfollow() {
        val userToFollow = getData()
        val currentUserId = userCache.userId() ?: return
        val updatedFollowers =
            userToFollow?.followers?.toMutableList()?.apply { remove(currentUserId) } ?: return
        val updatedUserToFollow = userToFollow.withFollowers(updatedFollowers)
        executeIO(
            Dispatchers.IO,
            onBefore = { emit(ViewModelState.SuccessState(updatedUserToFollow)) },
            execute = {
                unfollowUserInteractor.execute(
                    currentUserId, updatedUserToFollow.id ?: ""
                )
            },
            onFail = { emit(ViewModelState.SuccessState(userToFollow)) },
        )
    }
}