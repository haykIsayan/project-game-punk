package com.example.project_game_punk.features.profile

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.interactors.game.GetUserTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.game.UpdateGameProgressInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.common.update
import com.example.project_game_punk.features.discover.recommended.GameSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileLibraryViewModel @Inject constructor(
    private val getUserTrackedGamesInteractor: GetUserTrackedGamesInteractor,
    private val userCache: UserCache,
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
    private val trackedGamesCache: TrackedGamesCache,
    private val updateGameProgressInteractor: UpdateGameProgressInteractor,
): StateViewModel<List<GameEntity>, String>() {

    fun updateGameProgress(game: GameEntity, gameProgress: GameProgressStatus) {
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGames(game.updateGameProgressStatus(gameProgress)) },
            execute = { updateGameProgressInteractor.execute(game, gameProgress) },
            onFail = { updateGames(game) },
        )
    }

    private fun updateGames(game: GameEntity) {
        val games = getData()
        val updatedGames = games?.toMutableList()?.apply { update(game) }?.toList()
        updatedGames?.apply { emit(GameSuccessState(updatedGames)) }
    }

    override suspend fun loadData(param: String?): List<GameEntity> {
        return getUserTrackedGamesInteractor.execute(param)

//        param?.let { userId ->
//            getGameCollectionInteractor.execute(id = "main", userId = userId)?.games
//        } ?: trackedGamesCache.getMainGameCollection()?.games ?: emptyList()


//        val userId = param ?: userCache.loadAndCacheUser()?.id ?: return emptyList()
//        val games = trackedGamesCache.getMainGameCollection()?.games ?: emptyList()
//        return games
    }
}