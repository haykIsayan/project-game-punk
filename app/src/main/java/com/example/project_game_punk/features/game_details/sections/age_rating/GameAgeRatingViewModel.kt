package com.example.project_game_punk.features.game_details.sections.age_rating

import com.example.game_punk_domain.domain.entity.GameAgeRatingEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameAgeRatingInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameAgeRatingViewModel @Inject constructor(
    private val getGameAgeRatingInteractor: GetGameAgeRatingInteractor
): StateViewModel<GameAgeRatingEntity?, String>(){
    override suspend fun loadData(param: String?): GameAgeRatingEntity? {
        if (param == null) return null
        return getGameAgeRatingInteractor.execute(param)
    }
}