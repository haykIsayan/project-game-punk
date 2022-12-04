package com.example.project_game_punk.data.game.rawg.models

import androidx.room.*
import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.entity.GameEntity

@Entity
data class GameCollectionModel(
    @PrimaryKey(autoGenerate = true) var uuid: Long,
    @ColumnInfo(name = "id") override val id: String?,
    @ColumnInfo(name = "name") override val name: String?,
    @ColumnInfo(name = "games") override val games: List<GameEntity> = emptyList()
): GameCollectionEntity {

    override fun withGames(games: List<GameEntity>) = copy(games = games)
}