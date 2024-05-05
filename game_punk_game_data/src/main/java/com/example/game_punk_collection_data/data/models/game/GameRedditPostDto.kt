package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_domain.domain.entity.GameRedditPostEntity

data class GameRedditPostDto(
    override val image: String? = null,
    override val name: String,
    override val text: String,
    override val url: String,
    val username: String
) : GameRedditPostEntity {
    override val redditUsername: String
        get() = username
}