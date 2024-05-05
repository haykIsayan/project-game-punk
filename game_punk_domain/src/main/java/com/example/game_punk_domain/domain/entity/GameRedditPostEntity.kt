package com.example.game_punk_domain.domain.entity

interface GameRedditPostEntity {
    val redditUsername: String
    val image: String?
    val name: String
    val text: String
    val url: String
}