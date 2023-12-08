package com.example.project_game_punk.features.game_details.sections.developer_publisher

import com.example.game_punk_domain.domain.entity.GameCompanyEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameCompaniesInteractor
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDeveloperPublisherViewModel @Inject constructor(
    private val getGameCompaniesInteractor: GetGameCompaniesInteractor
): StateViewModel<List<GameCompanyEntity>, String>(){
    override suspend fun loadData(param: String?): List<GameCompanyEntity> {
        if (param == null) return emptyList()
        return getGameCompaniesInteractor.execute(param)
    }
}