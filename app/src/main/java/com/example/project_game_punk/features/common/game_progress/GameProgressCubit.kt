package com.example.project_game_punk.features.common.game_progress

import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.domain.entity.GameProgress
import com.example.project_game_punk.domain.interactors.game.UpdateGameProgressInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameProgressCubit(
    private val updateGameProgressInteractor: UpdateGameProgressInteractor
) {


    fun updateGameProgress(game: GameEntity, gameProgress: GameProgress) {

    }







    fun <T> executeIO(
        coroutineScope: CoroutineScope,
        ioDispatcher: CoroutineDispatcher,
        execute: suspend () -> T,
        onBefore: () -> Unit,
        onFail: (Exception) -> Unit,
        onSuccess: (T) -> Unit = {}
    ) {
        coroutineScope.launch {
            try {
                onBefore.invoke()
                val data = withContext(ioDispatcher) { execute.invoke() }
                onSuccess.invoke(data)
            } catch (e: Exception) {
                onFail.invoke(e)
            }
        }
    }
}