package com.example.game_punk_collection_data.data.models.user

import com.example.game_punk_domain.domain.entity.user.UserEntity


data class UserModel(
    override val id: String?,
    override val email: String,
    override val displayName: String,
    override val password: String,
    override val profileIcon: String
): UserEntity
