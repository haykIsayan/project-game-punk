package com.example.game_punk_collection_data.data.user

import androidx.room.*
import com.example.game_punk_collection_data.data.models.user.UserModel


@Dao
interface UserDao {

    @Query("SELECT * FROM UserModel WHERE email == :email")
    fun getUserByEmail(email: String): UserModel?

    @Query("SELECT * FROM UserModel WHERE uuid == :id")
    fun getUserById(id: String): UserModel

    @Update
    fun updateUser(user: UserModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createUser(user: UserModel)

}