package com.example.project_game_punk.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.game_punk_collection_data.data.game.GameCache
import com.example.game_punk_collection_data.data.game.idgb.GameIDGBDataSource
import com.example.game_punk_collection_data.data.game.idgb.IGDBClientInterceptor
import com.example.game_punk_collection_data.data.game.idgb.api.IDGBApi
import com.example.game_punk_collection_data.data.game.idgb.api.IDGBAuthApi
import com.example.game_punk_collection_data.data.game.rawg.RawgApi
import com.example.game_punk_collection_data.data.game.rawg.RawgClientInterceptor
import com.example.game_punk_collection_data.data.game.twitch.TwitchApi
import com.example.game_punk_collection_data.data.game_collection.GameCollectionFireStoreSource
import com.example.game_punk_collection_data.data.news.GameNewsDataSource
import com.example.game_punk_collection_data.data.news.GamingNewsApi
import com.example.game_punk_collection_data.data.news.SteamNewsApi
import com.example.game_punk_collection_data.data.review.ReviewFireStoreSource
import com.example.game_punk_collection_data.data.user.UserFireStoreDataSource
import com.example.game_punk_domain.domain.entity.post.PostEntity
import com.example.game_punk_domain.domain.entity.post.PostQueryModel
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.GameNewsRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.interfaces.PostRepository
import com.example.game_punk_domain.domain.interfaces.ReviewRepository
import com.example.game_punk_domain.domain.interfaces.UserRepository
import com.example.project_game_punk.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun providesReviewRepository(): ReviewRepository {
        return ReviewFireStoreSource()
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        @ApplicationContext context: Context,
        gameCollectionRepository: GameCollectionRepository
    ): UserRepository {
        return UserFireStoreDataSource(
            scope = GlobalScope,
            gameCollectionRepository = gameCollectionRepository
        )
    }

    @Provides
    @Singleton
    fun providesGameNewsRepository(
        @ApplicationContext context: Context,
        gameRepository: GameRepository
    ): GameNewsRepository {
        val gamingNewsApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.news_api_base_url)).build()
            .create(GamingNewsApi::class.java)
        val steamNewsApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.steam_api_base_url)).build()
            .create(SteamNewsApi::class.java)
        return GameNewsDataSource(
            gamingNewsApi,
            steamNewsApi,
            gameRepository
        )
    }

    @Provides
    @Singleton
    fun providesGameRepository(@ApplicationContext context: Context): GameRepository {

        val key = context.getString(R.string.rawg_api_key)

        val twitchApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.twitch_api_base_url)).build()
            .create(TwitchApi::class.java)

        val idgbAuthApi = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.idgb_auth_api_base_url)).build()
            .create(IDGBAuthApi::class.java)

        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 4
        dispatcher.maxRequestsPerHost = 1

        val idgbApi = Retrofit.Builder()
            .client(
                OkHttpClient
                .Builder()
                    .cache(cache)
                    .dispatcher(dispatcher)
                .addInterceptor(IGDBClientInterceptor())
                .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.idgb_api_base_url)).build()
            .create(IDGBApi::class.java)

        val rawgApi = Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(RawgClientInterceptor(key))
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(context.getString(R.string.rawg_api_base_url)).build()
            .create(RawgApi::class.java)

        return GameIDGBDataSource(
            context.getString(R.string.idgb_client_id),
            context.getString(R.string.idgb_client_secret),
            idgbApi,
            rawgApi,
            idgbAuthApi,
            twitchApi,
            GameCache(),
            GlobalScope
        )
    }

    @Provides
    @Singleton
    fun providesGameCollectionRepository(
        gameRepository: GameRepository
    ): GameCollectionRepository {
        return GameCollectionFireStoreSource(
            scope = GlobalScope,
            gameRepository
        )
    }

    @Provides
    @Singleton
    fun provides(

    ): PostRepository {
        return object : PostRepository {
            override suspend fun getPosts(postQuery: PostQueryModel): List<PostEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun createPost(post: PostEntity): PostEntity {
                TODO("Not yet implemented")
            }

        }
    }
}