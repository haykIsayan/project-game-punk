package com.example.game_punk_collection_data.data.game_collection

import androidx.room.*


@Dao
interface GameCollectionDao {

    @Query("SELECT * FROM gamecollectionmodel WHERE id == :id AND user_id = :userId")
    fun getGameCollection(
        id: String,
        userId: String
    ): GameCollectionModel

    @Update
    fun updateGameCollection(gameCollection: GameCollectionModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createGameCollection(gameCollection: GameCollectionModel)
}
