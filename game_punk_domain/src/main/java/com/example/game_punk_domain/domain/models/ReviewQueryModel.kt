package com.example.game_punk_domain.domain.models

data class ReviewQueryModel(
//    val id: String,
    val userIds: List<String>,
    val gameId: String = "",
    val limit: Int = 10
//    val timestamp: String
)
