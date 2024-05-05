package com.example.project_game_punk.features.game_details.sections.discussions

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameRedditPostEntity
import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameAchievementsInteractor
import com.example.game_punk_domain.domain.interactors.game.GetGameRecentRedditPostsInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.game_details.sections.achievements.GameAchievementState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameRecentRedditPostsViewModel @Inject constructor(
    private val getGameRecentRedditPostsInteractor: GetGameRecentRedditPostsInteractor
): StateViewModel<List<GameRedditPostEntity>, String>() {

    override suspend fun loadData(param: String?): List<GameRedditPostEntity> {
        val gameId = param ?: return emptyList()
        return getGameRecentRedditPostsInteractor.execute(gameId)
    }


}