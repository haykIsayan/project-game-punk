package com.example.project_game_punk.features.discover.featured

import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.StateViewModel
import com.example.game_punk_domain.domain.interactors.game.GetFeaturedGameInteractor
import com.example.project_game_punk.features.common.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class FeaturedGameSuccessState(game: GameEntity): ViewModelState.SuccessState<GameEntity>(game) {
    override fun equals(other: Any?): Boolean {
        val otherState = other as? FeaturedGameSuccessState ?: return false
        return otherState.data == data && otherState.data.isAdded == data.isAdded
    }
}

@HiltViewModel
class FeaturedGameViewModel @Inject constructor(
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