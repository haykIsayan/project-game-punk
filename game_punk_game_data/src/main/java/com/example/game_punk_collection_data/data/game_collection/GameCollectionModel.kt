package com.example.game_punk_collection_data.data.game_collection

import androidx.room.*
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity

@Entity
data class GameCollectionModel(
    @PrimaryKey(autoGenerate = true) var uuid: Long,
    @ColumnInfo(name = "id") override val id: String?,
    @ColumnInfo(name = "name") override val name: String?,
    @ColumnInfo(name = "games") override val games: List<GameEntity> = emptyList()
): GameCollectionEntity {

    override fun withGames(games: List<GameEntity>) = copy(games = games)
}