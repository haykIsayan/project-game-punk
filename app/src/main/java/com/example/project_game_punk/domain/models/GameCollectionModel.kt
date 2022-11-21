package com.example.project_game_punk.domain.models

import androidx.room.*

@Entity
data class GameCollectionModel(
    @PrimaryKey(autoGenerate = true) var uuid: Long,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "games")
    val games: List<GameModel> = emptyList()
)
