package com.example.game_punk_collection_data.data.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.game_punk_collection_data.data.models.user.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}