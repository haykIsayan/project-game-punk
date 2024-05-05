package com.example.project_game_punk.di.modules

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.user.GetCurrentUserInteractor
import com.example.game_punk_domain.domain.interactors.user.IsUserSessionActiveInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideMainGameCollectionCache(
        getTrackedGamesInteractor: GetTrackedGamesInteractor
    ): TrackedGamesCache {
        return TrackedGamesCache(getTrackedGamesInteractor)
    }

    @Provides
    @Singleton
    fun provideUserCache(
        getCurrentUserInteractor: GetCurrentUserInteractor,
        isUserSessionActiveInteractor: IsUserSessionActiveInteractor
    ): UserCache {
        return UserCache(
            getCurrentUserInteractor,
            isUserSessionActiveInteractor
        )
    }
}