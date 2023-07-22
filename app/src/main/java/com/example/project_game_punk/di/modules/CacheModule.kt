package com.example.project_game_punk.di.modules

import com.example.game_punk_domain.domain.TrackedGamesCache
import com.example.game_punk_domain.domain.interactors.game_collection.tracking.GetTrackedGamesInteractor
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
}