package com.example.game_punk_collection_data.data.game_collection

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.game_punk_collection_data.data.game.rawg.models.GameModel


@Database(entities = [GameCollectionModel::class, GameModel::class], version = 5)
@TypeConverters(GameCollectionConverters::class)
abstract class GameCollectionDatabase: RoomDatabase() {
    abstract fun gameCollectionDao(): GameCollectionDao
}