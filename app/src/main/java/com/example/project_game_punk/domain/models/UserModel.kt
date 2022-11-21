package com.example.project_game_punk.domain.models

import androidx.room.Entity

@Entity
data class UserModel(
     val uuid: Int,
    val id: String
)