package com.example.project_game_punk.di.modules

import com.example.game_punk_collection_data.data.models.game.GameCollectionFactoryImpl
import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interactors.GetAllAvailableGameGenresInteractor
import com.example.game_punk_domain.domain.interactors.GetAllAvailableGamePlatformsInteractor
import com.example.game_punk_domain.domain.interactors.game.*
import com.example.game_punk_domain.domain.interactors.game_collection.AddGameToGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.CreateGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.GetGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.RemoveGameFromGameCollectionInteractor
import com.example.game_punk_domain.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.news.GetNewsForGameInteractor
import com.example.game_punk_domain.domain.interactors.user.*
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.interfaces.UserRepository
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
    fun providesGetGameQueryWithUpcomingDatesInteractor(): GetGameQueryWithUpcomingDatesInteractor {
        return GetGameQueryWithUpcomingDatesInteractor()
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
    fun providesGetUpcomingGamesInteractor(
        getGameQueryWithUpcomingDatesInteractor: GetGameQueryWithUpcomingDatesInteractor,
        getGamesInteractor: GetGamesInteractor,
    ): GetUpcomingGamesInteractor {
        return GetUpcomingGamesInteractor(
            getGameQueryWithUpcomingDatesInteractor,
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
        userCache: UserCache,
        getGameCollectionInteractor: GetGameCollectionInteractor,
        createGameCollectionInteractor: CreateGameCollectionInteractor,
    ): GetTrackedGamesInteractor {
        return GetTrackedGamesInteractor(
            userCache,
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
        return UpdateGameProgressInteractor(
            gameCollectionRepository,
            addGameToGameCollectionInteractor,
            removeGameFromGameCollectionInteractor,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesFavoriteUnFavoriteGameInteractor(
        gameCollectionRepository: GameCollectionRepository,
        trackedGamesCache: TrackedGamesCache,
    ): FavoriteUnFavoriteGameInteractor {
        return FavoriteUnFavoriteGameInteractor(
            gameCollectionRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesUpdateUserScoreInteractor(
        gameCollectionRepository: GameCollectionRepository,
        trackedGamesCache: TrackedGamesCache,
    ): UpdateUserScoreInteractor {
        return UpdateUserScoreInteractor(
            gameCollectionRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesUpdateUserReviewInteractor(
        gameCollectionRepository: GameCollectionRepository,
        trackedGamesCache: TrackedGamesCache,
    ): UpdateUserReviewInteractor {
        return UpdateUserReviewInteractor(
            gameCollectionRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesUpdateGameExperiencePlatformInteractor(
        gameCollectionRepository: GameCollectionRepository,
        trackedGamesCache: TrackedGamesCache,
    ): UpdateGameExperiencePlatformInteractor {
        return UpdateGameExperiencePlatformInteractor(
            gameCollectionRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesUpdateGameExperienceStoreInteractor(
        gameCollectionRepository: GameCollectionRepository,
        trackedGamesCache: TrackedGamesCache,
    ): UpdateGameExperienceStoreInteractor {
        return UpdateGameExperienceStoreInteractor(
            gameCollectionRepository,
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesGetUserFavoriteGamesInteractor(
        trackedGamesCache: TrackedGamesCache,
    ): GetUserFavoriteGamesInteractor {
        return GetUserFavoriteGamesInteractor(
            trackedGamesCache
        )
    }

    @Provides
    @Singleton
    fun providesGetNowPlayingInteractor(
        applyGameMetaInteractor: ApplyGameMetaInteractor,
        trackedGamesCache: TrackedGamesCache,
        gameRepository: GameRepository
    ): GetNowPlayingGamesInteractor {
        return GetNowPlayingGamesInteractor(
            applyGameMetaInteractor,
            trackedGamesCache,
            gameRepository
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
    fun providesGetGameReleaseDateInteractor(
        gameRepository: GameRepository
    ): GetGameReleaseDateInteractor {
        return GetGameReleaseDateInteractor(
            gameRepository
        )
    }

    @Provides
    @Singleton
    fun providesGetAllAvailableGamePlatforms(
        gameRepository: GameRepository
    ): GetAllAvailableGamePlatformsInteractor {
        return GetAllAvailableGamePlatformsInteractor(
            gameRepository
        )
    }

    @Provides
    @Singleton
    fun providesGetAllAvailableGameGenres(
        gameRepository: GameRepository
    ): GetAllAvailableGameGenresInteractor {
        return GetAllAvailableGameGenresInteractor(
            gameRepository
        )
    }

    @Provides
    @Singleton
    fun providesGetGameGenresInteractor(
        gameRepository: GameRepository
    ): GetGameGenresInteractor {
        return GetGameGenresInteractor(
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

    @Provides
    @Singleton
    fun provideGetGameArtworksInteractor(
        gameRepository: GameRepository
    ): GetGameArtworksInteractor {
        return GetGameArtworksInteractor(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGetSimilarGamesInteractor(
        gameRepository: GameRepository
    ): GetSimilarGamesInteractor {
        return GetSimilarGamesInteractor(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGetGamesDLCsInteractor(
        gameRepository: GameRepository
    ): GetGamesDLCsInteractor {
        return GetGamesDLCsInteractor(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGetGameAgeRatingInteractor(
        gameRepository: GameRepository
    ): GetGameAgeRatingInteractor {
        return GetGameAgeRatingInteractor(gameRepository)
    }


    @Provides
    @Singleton
    fun provideGetGameCompaniesInteractor(
        gameRepository: GameRepository
    ): GetGameCompaniesInteractor {
        return GetGameCompaniesInteractor(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGetGameStoresInteractor(
        gameRepository: GameRepository
    ): GetGameStoresInteractor {
        return GetGameStoresInteractor(gameRepository)
    }

    @Provides
    @Singleton
    fun provideUserSignInInteractor(
        userCache: UserCache,
        trackedGamesCache: TrackedGamesCache,
        userRepository: UserRepository
    ): UserSignInInteractor {
        return UserSignInInteractor(
            userCache,
            trackedGamesCache,
            userRepository
        )
    }

    @Provides
    @Singleton
    fun provideUserSignUpInteractor(
        userCache: UserCache,
        trackedGamesCache: TrackedGamesCache,
        userRepository: UserRepository
    ): UserSignUpInteractor {
        return UserSignUpInteractor(
            userCache,
            trackedGamesCache,
            userRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetUserInteractor(
        userRepository: UserRepository
    ): GetUserInteractor {
        return GetUserInteractor(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserSignOutInteractor(
        userCache: UserCache,
        trackedGamesCache: TrackedGamesCache,
        userRepository: UserRepository
    ): UserSignOutInteractor {
        return UserSignOutInteractor(
            userCache,
            trackedGamesCache,
            userRepository
        )
    }

    @Provides
    @Singleton
    fun provideIsUserSessionActiveInteractor(
        userRepository: UserRepository
    ): IsUserSessionActiveInteractor {
        return IsUserSessionActiveInteractor(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserInteractor(
        userRepository: UserRepository
    ): GetCurrentUserInteractor {
        return GetCurrentUserInteractor(userRepository)
    }

}

