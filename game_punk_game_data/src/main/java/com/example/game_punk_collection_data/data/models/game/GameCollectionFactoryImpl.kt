package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_collection_data.data.game_collection.GameCollectionModel
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameCollectionFactory
import com.example.game_punk_domain.domain.entity.GameEntity

class GameCollectionFactoryImpl: GameCollectionFactory {
    override fun createGameCollection(
        userId: String,
        id: String,
        name: String,
        games: List<GameEntity>
    ): GameCollectionEntity {
        return GameCollectionModel(
            userId = userId,
            id = id,
            name = name,
            gameModels = games.map {
                it as GameModel
            }
        )
    }
}