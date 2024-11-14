package com.example.game_punk_collection_data.data.game

import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.models.GameQueryModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class GameCacheState {

    object Unavailable: GameCacheState()

    object Loading: GameCacheState()

    data class Available(val games: List<GameEntity>): GameCacheState()
}

class GameCache {

    companion object {
        const val CACHE_LIMIT = 100
        val cacheQueue = ArrayDeque<GameQueryModel>()
        val gamesCache = mutableMapOf<GameQueryModel, MutableStateFlow<GameCacheState>>()
    }

    fun getGameCacheState(gameQuery: GameQueryModel): MutableStateFlow<GameCacheState> {
        if (gamesCache[gameQuery] == null) {
            while (cacheQueue.size > CACHE_LIMIT) {
                val query = cacheQueue.removeFirst()
                gamesCache.remove(query)
            }
            cacheQueue.addLast(gameQuery)
            gamesCache[gameQuery] = MutableStateFlow(GameCacheState.Unavailable)
        }
        return gamesCache[gameQuery] ?: MutableStateFlow(GameCacheState.Unavailable)
    }

    suspend fun markForCaching(gameQuery: GameQueryModel) {
        val gameCache = getGameCacheState(gameQuery)
        gameCache.emit(GameCacheState.Loading)
    }

    suspend fun cacheGames(gameQuery: GameQueryModel, games: List<GameEntity>) {
        val gameCache = getGameCacheState(gameQuery)
        gameCache.emit(GameCacheState.Available(games))
    }
}