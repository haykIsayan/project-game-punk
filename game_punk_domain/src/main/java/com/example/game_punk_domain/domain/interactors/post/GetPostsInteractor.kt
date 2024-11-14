package com.example.game_punk_domain.domain.interactors.post

import com.example.game_punk_domain.domain.entity.post.PostEntity
import com.example.game_punk_domain.domain.entity.post.PostQueryModel
import com.example.game_punk_domain.domain.interfaces.PostRepository

class GetPostsInteractor(
    private val postRepository: PostRepository
) {
    suspend fun execute(postQuery: PostQueryModel): List<PostEntity> {
        return postRepository.getPosts(postQuery)
    }
}