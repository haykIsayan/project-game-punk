package com.example.project_game_punk.features.game_collection.game_collection_details

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.DeleteGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.UpdateGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameCollectionDetailsViewModel @Inject constructor(
    private val userCache: UserCache,
    private val getGameCollectionInteractor: GetGameCollectionInteractor,
    private val updateGameCollectionInteractor: UpdateGameCollectionInteractor,
    private val deleteGameCollectionInteractor: DeleteGameCollectionInteractor,
//    private val createGameCollectionInteractor: CreateGameCollectionInteractor,
    private val addGameToGameCollectionInteractor: AddGameToGameCollectionInteractor,
    private val removeGameFromGameCollectionInteractor: RemoveGameFromGameCollectionInteractor
) : StateViewModel<GameCollectionState, String>() {

    override suspend fun loadData(param: String?): GameCollectionState {
        if (param == null) return GameCollectionState(
            null,
            false
        )
        val userId = userCache.loadAndCacheUser()?.id ?: return GameCollectionState(
            null,
            false
        )
        val gameCollection = getGameCollectionInteractor.execute(param, userId)
        return GameCollectionState(gameCollection, false)
    }

    fun addGameToGameCollection(game: GameEntity, gameCollection: GameCollectionEntity) {
        val games = gameCollection.games
        val updatedGames = games.toMutableList().apply { add(game) }
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGameCollection(gameCollection.withGames(updatedGames)) },
            execute = { addGameToGameCollectionInteractor.execute(game, gameCollection) },
            onFail = { updateGameCollection(gameCollection) },
        )
    }

    private fun updateGameCollection(
        gameCollection: GameCollectionEntity
    ) {
        val gameCollectionState = getData() ?: return
        val newGameCollectionState = gameCollectionState.copy(gameCollection = gameCollection)
        emit(ViewModelState.SuccessState(newGameCollectionState))
    }

    fun removeGameFromGameCollection(game: GameEntity, gameCollection: GameCollectionEntity) {
        val games = gameCollection.games
        val updatedGames = games.toMutableList().apply { remove(game) }
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGameCollection(gameCollection.withGames(updatedGames)) },
            execute = { removeGameFromGameCollectionInteractor.execute(game, gameCollection) },
            onFail = { updateGameCollection(gameCollection) },
        )
    }

    fun updateGameCollectionName(
        name: String,
        gameCollection: GameCollectionEntity
    ) {
        val updatedGameCollection = gameCollection.withName(name)
        executeIO(
            Dispatchers.IO,
            onBefore = { updateGameCollection(updatedGameCollection) },
            execute = { updateGameCollectionInteractor.execute(updatedGameCollection) },
            onFail = { updateGameCollection(gameCollection) },
        )
    }

    fun deleteGameCollection(
        gameCollection: GameCollectionEntity,
        onGameCollectionDeleted: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val gameCollectionState = getData() ?: return@launch
                emit(ViewModelState.SuccessState(gameCollectionState.copy(isLoading = true)))
                deleteGameCollectionInteractor.execute(gameCollection)
                emit(ViewModelState.SuccessState(gameCollectionState.copy(isLoading = false)))
                onGameCollectionDeleted()
            } catch (e: Exception) {
                println(e.toString())
//                emit(ViewModelState.SuccessState(gameCollectionState.copy(isLoading = true)))
            }
        }
    }

}

data class GameCollectionState(
    val gameCollection: GameCollectionEntity?,
    val isLoading: Boolean
)