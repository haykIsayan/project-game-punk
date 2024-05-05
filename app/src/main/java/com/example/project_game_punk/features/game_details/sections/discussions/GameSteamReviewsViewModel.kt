package com.example.project_game_punk.features.game_details.sections.discussions

import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameSteamReviewsViewModel @Inject constructor(

): StateViewModel<List<GameSteamReviewState>, String>() {

    override suspend fun loadData(param: String?): List<GameSteamReviewState> {
        TODO("Not yet implemented")
    }

}



data class GameSteamReviewState(
//    val
    val review: GameReviewEntity
)