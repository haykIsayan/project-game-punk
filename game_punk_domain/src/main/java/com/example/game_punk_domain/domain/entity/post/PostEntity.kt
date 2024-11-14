package com.example.game_punk_domain.domain.entity.post

interface PostEntity {
    val authorId: String
    val taggedUserIds: List<String>
    val gameIds: List<String>
    val text: String

    fun updateText(text: String)

}