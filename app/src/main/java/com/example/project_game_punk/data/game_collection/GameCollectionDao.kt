package com.example.project_game_punk.data.game_collection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.project_game_punk.domain.models.GameCollectionModel

@Dao
interface GameCollectionDao {

    @Query("SELECT * FROM gamecollectionmodel WHERE id == :id")
    fun getGameCollection(id: String): GameCollectionModel

    @Update
    fun updateGameCollection(gameCollection: GameCollectionModel)

    @Insert
    fun createGameCollection(gameCollection: GameCollectionModel)
}
