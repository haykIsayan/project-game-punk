package com.example.game_punk_collection_data.data.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.game_punk_collection_data.data.models.user.UserModel
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserLocalDataSource(
    private val dataStore: DataStore<Preferences>,
    private val userDatabase: UserDatabase
): UserRepository {

    private val userIdPreferences = stringPreferencesKey(USER_ID_PREFERENCES)

    override suspend fun createUser(userAuthModel: UserAuthModel): UserEntity {
        val user =  userDatabase.userDao().getUserByEmail(userAuthModel.email)
        if (user == null) {
            val userModel = UserModel(
                uuid = 0,
                email = userAuthModel.email,
                displayName = userAuthModel.displayName,
                password = userAuthModel.password,
                profileIcon = userAuthModel.profileIcon
            )
            val encryptedPassword = encryptPassword(userModel.password)
            val encryptedUserModel = userModel.copy(password = encryptedPassword)
            userDatabase.userDao().createUser(encryptedUserModel)
            return userDatabase.userDao().getUserByEmail(userModel.email) ?: userModel
        } else {
            throw EmailAlreadyInUseException()
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): UserEntity {
        val user = userDatabase.userDao().getUserByEmail(email) ?: throw UserNotFoundException()
        val encryptedPassword = encryptPassword(password)
        return if (encryptedPassword == user.password) {
            user
        } else {
            throw IncorrectPasswordException()
        }
    }

    override suspend fun getUserId(userId: String): UserEntity {
        return userDatabase.userDao().getUserById(userId)
    }

    override suspend fun setUserSession(userId: String?) {
        dataStore.edit { preferences ->
            userId?.let {
                preferences[userIdPreferences] = userId
            } ?: preferences.remove(userIdPreferences)
        }
    }

    override suspend fun getUserSession(): String {

        return dataStore.data.first()[userIdPreferences] ?: ""
    }

    private fun encryptPassword(password: String): String {
        return password
    }


    companion object {
        const val USER_ID_PREFERENCES = "user_id_preferences"
    }

}

class UserNotFoundException: Exception()

class IncorrectPasswordException: Exception()

class EmailAlreadyInUseException: Exception()