package com.example.project_game_punk.di.modules

import android.content.Context
import androidx.room.Room
import com.example.project_game_punk.R
import com.example.project_game_punk.data.game.GameDataSource
import com.example.project_game_punk.data.game.RawgApi
import com.example.project_game_punk.data.game.RawgClientInterceptor
import com.example.project_game_punk.data.game_collection.GameCollectionDataSource
import com.example.project_game_punk.data.game_collection.GameCollectionDatabase
import com.example.project_game_punk.domain.interfaces.GameCollectionRepository
import com.example.project_game_punk.domain.interfaces.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesGameRepository(@ApplicationContext context: Context): GameRepository {
        val key = context.getString(R.string.rawg_api_key)
        val rawgApi = Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(RawgClientInterceptor(key))
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.rawg_api_base_url)).build()
            .create(RawgApi::class.java)
        return GameDataSource(rawgApi)
    }

    @Provides
    @Singleton
    fun providesGameCollectionRepository(
        @ApplicationContext context: Context,
        gameRepository: GameRepository
    ): GameCollectionRepository {
        val gameCollectionDatabase = Room.databaseBuilder(
            context,
            GameCollectionDatabase::class.java,
            "game-collection-database-name"
        ).build()
        return GameCollectionDataSource(gameCollectionDatabase, gameRepository)
    }
}