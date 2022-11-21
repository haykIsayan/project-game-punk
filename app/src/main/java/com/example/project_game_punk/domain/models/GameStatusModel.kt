package com.example.project_game_punk.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameStatusModel(
    @PrimaryKey(autoGenerate = true) val uuid: Long,
    @ColumnInfo(name = "game_id") val gameId: String?,
    @ColumnInfo(name = "status") val status: Status?
)

enum class Status {
    followed,
    unfollowed,
}