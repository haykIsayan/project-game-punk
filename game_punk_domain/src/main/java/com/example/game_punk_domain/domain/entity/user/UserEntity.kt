package com.example.game_punk_domain.domain.entity.user

interface UserEntity {
    val id: String?
    val email: String?
    val displayName: String?
    val password: String?
    val profileIcon: String?
    val following: List<String>?
    val followers: List<String>?

    fun withFollowing(following: List<String>): UserEntity

    fun withFollowers(followers: List<String>): UserEntity

    fun toMap(): HashMap<String, Any?>
}