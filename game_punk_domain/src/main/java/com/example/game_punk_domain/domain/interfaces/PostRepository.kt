package com.example.game_punk_domain.domain.interfaces

import com.example.game_punk_domain.domain.entity.post.PostEntity
import com.example.game_punk_domain.domain.entity.post.PostQueryModel

interface PostRepository {
    suspend fun getPosts(postQuery: PostQueryModel): List<PostEntity>

    suspend fun createPost(post: PostEntity): PostEntity
}