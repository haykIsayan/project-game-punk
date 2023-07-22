package com.example.game_punk_collection_data.data.game.rawg.models

import com.example.game_punk_collection_data.data.game_collection.GameCollectionModel
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameCollectionFactory
import com.example.game_punk_domain.domain.entity.GameEntity

class GameCollectionFactoryImpl: GameCollectionFactory {
    override fun createGameCollection(
        id: String,
        name: String,
        games: List<GameEntity>
    ): GameCollectionEntity {
        return GameCollectionModel(
            uuid = 0,
            id = id,
            name = name,
            games = games
        )
    }
}