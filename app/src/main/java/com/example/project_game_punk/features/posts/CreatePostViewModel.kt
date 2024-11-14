package com.example.project_game_punk.features.posts

import com.example.game_punk_domain.domain.entity.post.PostEntity
import com.example.game_punk_domain.domain.entity.post.PostFactory
import com.example.game_punk_domain.domain.interactors.post.CreatePostInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postFactory: PostFactory,
    private val createPostInteractor: CreatePostInteractor
): StateViewModel<PostEntity, Unit>()  {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): PostEntity {
        return postFactory.createEmpty()
    }

}