package com.example.game_punk_collection_data.data.models.user

import com.example.game_punk_collection_data.data.game_collection.GameCollectionModel
import com.example.game_punk_collection_data.data.models.game.GameExperienceModel
import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_domain.domain.entity.user.UserEntity


data class UserModel(
    override val id: String? = null,
    override val email: String? = null,
    override val displayName: String? = null,
    override val password: String? = null,
    override val profileIcon: String? = null,
    override val following: List<String>? = emptyList(),
    override val followers: List<String>? = emptyList()
): UserEntity {
    override fun withFollowing(following: List<String>): UserEntity {
        return copy(following = following)
    }

    override fun withFollowers(followers: List<String>): UserEntity {
        return copy(followers = followers)
    }

    override fun toMap(): HashMap<String, Any?> = hashMapOf(
        "id" to (id ?: ""),
        "email" to (email ?: ""),
        "displayName" to (displayName ?: ""),
        "following" to following,
        "followers" to followers
    )

}
