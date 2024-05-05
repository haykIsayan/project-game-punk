package com.example.project_game_punk.features.profile.game_collection

import androidx.lifecycle.viewModelScope
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameCollectionFactory
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionsInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.game_punk_domain.domain.interactors.user.UserSignOutInteractor
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.executeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProfileGameCollectionsViewModel @Inject constructor(
    private val userCache: UserCache,
    private val gameCollectionFactory: GameCollectionFactory,
    private val getGameCollectionsInteractor: GetGameCollectionsInteractor,
    private val createGameCollectionInteractor: CreateGameCollectionInteractor
) : StateViewModel<ProfileGameCollectionState, String>() {

    override suspend fun loadData(param: String?): ProfileGameCollectionState {
        val userId = param ?: userCache.loadAndCacheUser()?.id ?: return ProfileGameCollectionState(
            gameCollections = emptyList()
        )
        val gameCollections = getGameCollectionsInteractor.execute(userId)
        return ProfileGameCollectionState(
            gameCollections = gameCollections
        )
    }

    fun createGameCollection(
        newCollectionTitle: String,
        onGameCollectionCreated: (gameCollection: GameCollectionEntity) -> Unit
    ) {
        val currentState = getData() ?: return

        viewModelScope.launch(Dispatchers.Main) {
            emit(ViewModelState.SuccessState(currentState.copy(isLoading = true)))

            try {
                val gameCollection = withContext(Dispatchers.IO) {
                    val userId = userCache.loadAndCacheUser()?.id ?: return@withContext null
                    val gameCollection = gameCollectionFactory.createGameCollection(
                        userId,
                        "",
                        newCollectionTitle,
                        emptyList()
                    )
                    createGameCollectionInteractor.execute(gameCollection)
                }
                emit(ViewModelState.SuccessState(currentState.copy(isLoading = false)))
                gameCollection?.let(onGameCollectionCreated)
            } catch (e: Exception) {
                emit(ViewModelState.SuccessState(currentState.copy(isLoading = false)))
            }
        }
    }

}

data class ProfileGameCollectionState(
    val gameCollections: List<GameCollectionEntity>,
    val isLoading: Boolean = false
)

