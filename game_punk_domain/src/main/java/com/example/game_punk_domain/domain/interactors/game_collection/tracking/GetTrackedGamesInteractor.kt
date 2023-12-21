package com.example.game_punk_domain.domain.interactors.game_collection.tracking

import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameCollectionFactory
import com.example.game_punk_domain.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache

class GetTrackedGamesInteractor(
    private val userCache: UserCache,
    private val gameCollectionFactory: GameCollectionFactory,
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
    private val createGameCollectionInteractor: CreateGameCollectionInteractor,
) {
    suspend fun execute(): GameCollectionEntity? {
        val mainCollection = getMainCollection()
        println(mainCollection)
        return mainCollection ?: createMainCollection()
    }

    private suspend fun getMainCollection(): GameCollectionEntity? {
        return userCache.loadAndCacheUser()?.id?.let { userId ->
            getGameCollectionInteractor.execute(id = "main", userId = userId)
        }
    }

    private suspend fun createMainCollection(): GameCollectionEntity? {
        return userCache.loadAndCacheUser()?.id?.let { userId ->
            createGameCollectionInteractor.execute(
                gameCollectionFactory.createGameCollection(
                    userId = userId,
                    id = "main",
                    name = "",
                    games = emptyList()
                )
            )
        }
    }
}