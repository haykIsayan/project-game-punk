package com.example.game_punk_collection_data.data.user

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.game_punk_collection_data.data.game_collection.GameCollectionConverters
import com.example.game_punk_collection_data.data.game_collection.GameCollectionDao
import com.example.game_punk_collection_data.data.game_collection.GameCollectionModel
import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_collection_data.data.models.user.UserModel






@Database(entities = [UserModel::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}