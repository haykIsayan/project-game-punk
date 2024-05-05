package com.example.game_punk_domain.domain.entity.user

data class UserCounterModel(
    override val title: String,
    val counter: String
): UserStatEntity