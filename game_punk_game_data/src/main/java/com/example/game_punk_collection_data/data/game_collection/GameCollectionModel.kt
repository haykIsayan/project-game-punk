package com.example.game_punk_collection_data.data.game_collection

import com.example.game_punk_collection_data.data.models.game.GameExperienceModel
import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity

data class GameCollectionModel(
    override val userId: String? = null,
    override val id: String? = null,
    override val name: String? = null,
    val gameModels: List<GameModel> = emptyList()
): GameCollectionEntity {

    fun toMap() = hashMapOf(
        USER_ID_FIELD to userId,
        ID_FIELD to id,
        NAME_FIELD to name,
        GAME_FIELD to games.map { game ->
            GameModel(
                id = game.id,
                gameExperienceModel = game.gameExperience as GameExperienceModel
            )
        }
    )

    override val games: List<GameEntity>
        get() = gameModels

    override fun withGames(games: List<GameEntity>): GameCollectionEntity {
        return copy(gameModels = games.map { it as GameModel }.toList())
    }

    companion object {
        const val USER_ID_FIELD = "userId"
        const val ID_FIELD = "id"
        const val NAME_FIELD = "name"
        const val GAME_FIELD = "games"
    }

}