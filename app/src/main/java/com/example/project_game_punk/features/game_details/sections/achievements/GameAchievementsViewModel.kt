package com.example.project_game_punk.features.game_details.sections.achievements

import android.util.Log
import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.entity.GameAchievementEntity
import com.example.game_punk_domain.domain.interactors.game.GetGameAchievementsInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.project_game_punk.features.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameAchievementsViewModel @Inject constructor(
    private val trackedGamesCache: TrackedGamesCache,
    private val getGameAchievementsInteractor: GetGameAchievementsInteractor
): StateViewModel<List<GameAchievementState>, String>() {

    override suspend fun loadData(param: String?): List<GameAchievementState> {
        val gameId = param ?: return emptyList()
        val achievements = getGameAchievementsInteractor.execute(gameId)
        Log.d("Haykk", "Here are the acheivements in the view model $achievements")
        val experience =
            trackedGamesCache.getMainGameCollection()?.games?.find {
                it.id == gameId
            }?.gameExperience /*?: return emptyList()*/

        return achievements.map { achievement ->
            GameAchievementState(
                isCompleted = experience?.completedAchievements?.contains(achievement.name) ?: false,
                gameAchievement = achievement
            )
        }
    }
}


data class GameAchievementState(
    val isCompleted: Boolean = false,
    val gameAchievement: GameAchievementEntity
)