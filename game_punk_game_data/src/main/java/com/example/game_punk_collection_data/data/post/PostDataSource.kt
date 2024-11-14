package com.example.game_punk_collection_data.data.post

import com.example.game_punk_domain.domain.entity.post.PostEntity
import com.example.game_punk_domain.domain.entity.post.PostQueryModel
import com.example.game_punk_domain.domain.interfaces.PostRepository

class PostDataSource: PostRepository {

    override suspend fun getPosts(postQuery: PostQueryModel): List<PostEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun createPost(post: PostEntity): PostEntity {
        TODO("Not yet implemented")
    }
}