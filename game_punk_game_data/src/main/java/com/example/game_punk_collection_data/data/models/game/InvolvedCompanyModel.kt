package com.example.game_punk_collection_data.data.models.game

data class InvolvedCompanyModel(
    val company: String,
    val game: String,
    val developer: Boolean,
    val publisher: Boolean,
    val supporting: Boolean
)