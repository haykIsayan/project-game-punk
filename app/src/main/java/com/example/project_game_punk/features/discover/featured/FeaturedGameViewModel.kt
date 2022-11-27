package com.example.project_game_punk.features.discover.featured

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.domain.interactors.game.GetFeaturedGameInteractor
import com.example.project_game_punk.domain.interactors.game_collection.tracking.TrackUntrackGameInteractor
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import com.example.project_game_punk.features.discover.recent.GameSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FeaturedGameSuccessState(game: GameEntity): ViewModelState.SuccessState<GameEntity>(game) {
    override fun equals(other: Any?): Boolean {
        val otherState = other as? FeaturedGameSuccessState ?: return false
        return otherState.data == data && otherState.data.isAdded == data.isAdded
    }
}

@HiltViewModel
class FeaturedGameViewModel @Inject constructor(
    private val trackUntrackGameInteractor: TrackUntrackGameInteractor,
    private val getFeaturedGameInteractor: GetFeaturedGameInteractor
): StateViewModel<GameEntity, Unit>() {

    init {
        loadState()
    }

    fun trackUntrackGame(game: GameEntity) {
//        executeIO(Dispatchers.IO,
//            onBefore = { emit(FeaturedGameSuccessState(game.copy(isAdded = !game.isAdded))) },
//            execute = { trackUntrackGameInteractor.execute(game) },
//            onFail = {emit(FeaturedGameSuccessState(game))}
//        )
    }

    override suspend fun loadData(param: Unit?): GameEntity {
        return getFeaturedGameInteractor.execute()
    }
}