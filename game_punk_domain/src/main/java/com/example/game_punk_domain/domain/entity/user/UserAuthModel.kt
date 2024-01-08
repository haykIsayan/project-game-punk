package com.example.game_punk_domain.domain.entity.user

data class UserAuthModel(
    val email: String,
    val displayName: String,
    val password: String,
    val profileIcon: String
) {

    companion object {
        fun emptyUserAuth(): UserAuthModel {
            return UserAuthModel(
                email = "",
                displayName = "",
                password = "",
                profileIcon = ""
            )
        }
    }

    fun createUser(id: String? = null): UserEntity {
        return object : UserEntity {
            override val id: String?
                get() = id
            override val email: String
                get() = this@UserAuthModel.email
            override val displayName: String?
                get() = this@UserAuthModel.displayName
            override val password: String?
                get() = this@UserAuthModel.password
            override val profileIcon: String?
                get() = this@UserAuthModel.profileIcon
        }
    }
}