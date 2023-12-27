package com.example.project_game_punk.di.modules

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
import com.example.game_punk_domain.domain.interactors.user.GetUserInteractor
import com.example.game_punk_domain.domain.interactors.user.UserCache
import com.example.game_punk_domain.domain.interfaces.UserRepository
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
        userRepository: UserRepository,
        getUserInteractor: GetUserInteractor
    ): UserCache {
        return UserCache(
            userRepository,
            getUserInteractor
        )
    }
}