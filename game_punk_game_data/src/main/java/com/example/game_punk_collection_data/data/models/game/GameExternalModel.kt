package com.example.game_punk_collection_data.data.models.game

data class GameExternalModel(
    val category: Int,
    val media: Int,
    val platform: String,
    val url: String,
    val uid: String? = null,
)