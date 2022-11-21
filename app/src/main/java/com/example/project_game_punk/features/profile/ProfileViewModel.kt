package com.example.project_game_punk.features.profile

import com.example.project_game_punk.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.project_game_punk.domain.models.GameCollectionModel
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val gameMainGameCollectionInteractor: GetTrackedGamesInteractor
): StateViewModel<GameCollectionModel, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): GameCollectionModel {
        return gameMainGameCollectionInteractor.execute()
    }

}