package com.example.game_punk_collection_data.data.models.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.game_punk_domain.domain.entity.user.UserEntity


@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true) var uuid: Long = 0,
    @ColumnInfo(name = "email") override val email: String,
    @ColumnInfo(name = "display_name") override val displayName: String,
    @ColumnInfo(name = "password") override val password: String,
    @ColumnInfo(name = "profile_icon") override val profileIcon: String
): UserEntity {
    override val id: String?
        get() = uuid.toString()
}
