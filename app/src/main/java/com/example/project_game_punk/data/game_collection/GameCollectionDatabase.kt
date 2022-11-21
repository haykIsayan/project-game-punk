package com.example.project_game_punk.data.game_collection

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.project_game_punk.domain.models.GameCollectionModel
import com.example.project_game_punk.domain.models.GameModel

@Database(entities = [GameCollectionModel::class, GameModel::class], version = 3)
@TypeConverters(GameCollectionConverters::class)
abstract class GameCollectionDatabase: RoomDatabase() {
    abstract fun gameCollectionDao(): GameCollectionDao
}