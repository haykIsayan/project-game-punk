package com.example.project_game_punk.di.modules

import com.example.project_game_punk.domain.TrackedGamesCache
import com.example.project_game_punk.domain.interactors.game.*
import com.example.project_game_punk.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.project_game_punk.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.interfaces.GameRepository
import com.example.project_game_punk.data.game.rawg.models.GameCollectionFactoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    @Singleton
    fun providesGetFeaturedGameInteractor(
        gameRepository: GameRepository,
        trackedGamesCache: TrackedGamesCache,
    ): GetFeaturedGameInteractor {
        return GetFeaturedGameInteractor(
            gameRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesSearchGamesInteractor(
        trackedGamesCache: TrackedGamesCache,
        gameRepository: GameRepository
    ): GetGamesInteractor {
        return GetGamesInteractor(trackedGamesCache, gameRepository)
    }

    @Provides
    @Singleton
    fun providesGetTrendingGamesInteractor(
        getGamesInteractor: GetGamesInteractor
    ): GetTrendingGamesInteractor {
        return GetTrendingGamesInteractor(getGamesInteractor)
    }

    @Provides
    @Singleton
    fun providesGetGameQueryWithRecentDatesInteractor(): GetGameQueryWithRecentDatesInteractor {
        return GetGameQueryWithRecentDatesInteractor()
    }

    @Provides
    @Singleton
    fun providesGetUserGameQueryInteractor(
        getGameQueryWithRecentDatesInteractor: GetGameQueryWithRecentDatesInteractor
    ): GetUserGameQueryInteractor {
        return GetUserGameQueryInteractor(getGameQueryWithRecentDatesInteractor)
    }

    @Provides
    @Singleton
    fun providesGetRecommendedGamesInteractor(
        getUserGameQueryInteractor: GetUserGameQueryInteractor,
        getGamesInteractor: GetGamesInteractor,
    ): GetRecommendedGamesInteractor {
        return GetRecommendedGamesInteractor(
            getUserGameQueryInteractor,
            getGamesInteractor
        )
    }

    @Provides
    @Singleton
    fun providesCreateGameCollectionInteractor(
        gameCollectionRepository: GameCollectionRepository
    ): CreateGameCollectionInteractor {
        return CreateGameCollectionInteractor(gameCollectionRepository)
    }

    @Provides
    @Singleton
    fun providesApplyGameMetaInteractor(
        gameRepository: GameRepository
    ): ApplyGameMetaInteractor {
        return ApplyGameMetaInteractor(gameRepository)
    }

    @Provides
    @Singleton
    fun providesGetMainGameCollectionInteractor(
        applyGameMetaInteractor: ApplyGameMetaInteractor,
        getGameCollectionInteractor: GetGameCollectionInteractor,
        createGameCollectionInteractor: CreateGameCollectionInteractor,
    ): GetTrackedGamesInteractor {
        return GetTrackedGamesInteractor(
            applyGameMetaInteractor,
            GameCollectionFactoryImpl(),
            getGameCollectionInteractor,
            createGameCollectionInteractor
        )
    }

    @Provides
    @Singleton
    fun providesAddGameToGameCollectionInteractor(
        gameCollectionRepository: GameCollectionRepository
    ): AddGameToGameCollectionInteractor {
        return AddGameToGameCollectionInteractor(gameCollectionRepository)
    }

    @Provides
    @Singleton
    fun providesRemoveGameFromGameCollectionInteractor(
        gameCollectionRepository: GameCollectionRepository
    ): RemoveGameFromGameCollectionInteractor {
        return RemoveGameFromGameCollectionInteractor(gameCollectionRepository)
    }

    @Provides
    @Singleton
    fun providesGetGameCollectionInteractor(
//        mainGameCollectionCache: MainGameCollectionCache,
        gameCollectionRepository: GameCollectionRepository
    ): GetGameCollectionInteractor {
        return GetGameCollectionInteractor(gameCollectionRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateGameProgressInteractor(
        gameCollectionRepository: GameCollectionRepository,
        addGameToGameCollectionInteractor: AddGameToGameCollectionInteractor,
        removeGameFromGameCollectionInteractor: RemoveGameFromGameCollectionInteractor,
        trackedGamesCache: TrackedGamesCache,
    ): UpdateGameProgressInteractor {
        return  UpdateGameProgressInteractor(
            gameCollectionRepository,
            addGameToGameCollectionInteractor,
            removeGameFromGameCollectionInteractor,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesGetNowPlayingInteractor(
        trackedGamesCache: TrackedGamesCache
    ): GetNowPlayingGamesInteractor {
        return GetNowPlayingGamesInteractor(trackedGamesCache)
    }
}

