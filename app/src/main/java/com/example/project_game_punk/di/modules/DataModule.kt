package com.example.project_game_punk.di.modules

import android.content.Context
import androidx.room.Room
import com.example.project_game_punk.R
import com.example.project_game_punk.data.game.idgb.GameIDGBDataSource
import com.example.project_game_punk.data.game.idgb.api.IDGBApi
import com.example.project_game_punk.data.game.idgb.api.IDGBAuthApi
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
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesGameRepository(@ApplicationContext context: Context): GameRepository {


//        val key = context.getString(R.string.rawg_api_key)

        val idgbAuthApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.idgb_auth_api_base_url)).build()
            .create(IDGBAuthApi::class.java)

        val idgbApi = Retrofit.Builder()
            .client(OkHttpClient.Builder()
//                .addInterceptor(RawgClientInterceptor(key))
                .build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.idgb_api_base_url)).build()
            .create(IDGBApi::class.java)
        return GameIDGBDataSource(
            context.getString(R.string.idgb_client_id),
            context.getString(R.string.idgb_client_secret),
            idgbApi,
            idgbAuthApi
        )

//        val key = context.getString(R.string.rawg_api_key)
//        val rawgApi = Retrofit.Builder()
//            .client(OkHttpClient.Builder()
//                .addInterceptor(RawgClientInterceptor(key))
//                .build())
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(context.getString(R.string.rawg_api_base_url)).build()
//            .create(RawgApi::class.java)
//        return GameRawgSource(rawgApi)
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
        ).fallbackToDestructiveMigration().build()
        return GameCollectionDataSource(gameCollectionDatabase, gameRepository)
    }
}