package com.example.game_punk_domain.domain.interactors.post

import com.example.game_punk_domain.domain.entity.post.PostEntity
import com.example.game_punk_domain.domain.interfaces.PostRepository

class CreatePostInteractor(
    private val postRepository: PostRepository
) {
    suspend fun createPost(post: PostEntity): PostEntity {
        return postRepository.createPost(post)
    }

}