package com.example.project_game_punk.di.modules

import com.example.game_punk_collection_data.data.game.rawg.models.GameCollectionFactoryImpl
import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interactors.game.*
import com.example.game_punk_domain.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository
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
    fun providesGetNewsForGameInteractor(
        gameNewsRepository: GameNewsRepository
    ): GetNewsForGameInteractor {
        return GetNewsForGameInteractor(
            gameNewsRepository
        )
    }


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
    fun providesGetRecentGamesInteractor(
        getGameQueryWithRecentDatesInteractor: GetGameQueryWithRecentDatesInteractor,
        getGamesInteractor: GetGamesInteractor,
    ): GetRecentGamesInteractor {
        return GetRecentGamesInteractor(
            getGameQueryWithRecentDatesInteractor,
            getGamesInteractor,
        )
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
        getGameCollectionInteractor: GetGameCollectionInteractor,
        createGameCollectionInteractor: CreateGameCollectionInteractor,
    ): GetTrackedGamesInteractor {
        return GetTrackedGamesInteractor(
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
        applyGameMetaInteractor: ApplyGameMetaInteractor,
        trackedGamesCache: TrackedGamesCache
    ): GetNowPlayingGamesInteractor {
        return GetNowPlayingGamesInteractor(
            applyGameMetaInteractor,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesGetGameInteractor(
        applyGameMetaInteractor: ApplyGameMetaInteractor,
        gameRepository: GameRepository,
        trackedGamesCache: TrackedGamesCache
    ): GetGameInteractor {
        return GetGameInteractor(
            applyGameMetaInteractor,
            gameRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesGetGamePlatformsInteractor(
        gameRepository: GameRepository
    ): GetGamePlatformsInteractor {
        return GetGamePlatformsInteractor(
            gameRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetGameScreenshotsInteractor(
        gameRepository: GameRepository
    ): GetGameScreenshotsInteractor {
        return GetGameScreenshotsInteractor(gameRepository)
    }
}

