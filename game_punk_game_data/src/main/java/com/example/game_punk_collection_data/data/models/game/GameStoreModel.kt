package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_domain.domain.entity.GameStoreEntity

data class GameStoreModel(
    override val slug: String,
    override val name: String,
    override val url: String? = null
): GameStoreEntity

val availableStores = mapOf(
    Pair(
        1,
        GameStoreModel(
            slug = "steam",
            name = "Steam"
        )
    ),
    Pair(
        11,
        GameStoreModel(
            slug = "microsoft",
            name = "Microsoft Store"
        )
    ),
    Pair(
        26,
        GameStoreModel(
            slug = "epic_game_store",
            name = "Epic Games"
        )
    ),
    Pair(
        28,
        GameStoreModel(
            slug = "oculus",
            name = "Oculus Store"
        )
    ),
    Pair(
        31,
        GameStoreModel(
            slug = "xbox_marketplace",
            name = "Xbox Marketplace"
        )
    ),
    Pair(
        36,
        GameStoreModel(
            slug = "playstation_store_us",
            name = "Playstation Store"
        )
    ),
    Pair(
        54,
        GameStoreModel(
            slug = "xbox_game_pass_ultimate_cloud",
            name = "Xbox Game Pass"
        )
    ),
    Pair(
        20,
        GameStoreModel(
            "amazon",
            "Amazon"
        )
    )
)