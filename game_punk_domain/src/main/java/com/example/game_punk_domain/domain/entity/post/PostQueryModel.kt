package com.example.game_punk_domain.domain.entity.post

data class PostQueryModel(
    val authorId: String,
    val taggedUserIds: List<String>,
    val gameIds: List<String>,
)
