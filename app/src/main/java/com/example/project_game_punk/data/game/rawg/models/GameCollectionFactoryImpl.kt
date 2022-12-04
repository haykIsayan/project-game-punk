package com.example.project_game_punk.data.game.rawg.models

import com.example.project_game_punk.domain.entity.GameCollectionEntity
import com.example.project_game_punk.domain.entity.GameCollectionFactory
import com.example.project_game_punk.domain.entity.GameEntity

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