package com.mock.musictpn.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mock.musictpn.datasource.local.AppDatabase
import com.mock.musictpn.datasource.network.ApiContract.BASE_URL
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.mediaplayer.MusicPlayer
import com.mock.musictpn.app.service.MusicService
import com.mock.musictpn.datasource.local.dao.PlayListDao
import com.mock.musictpn.model.track.TrackListInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHandler() = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    @Singleton
    @Provides
    fun provideIOScope(handler: CoroutineExceptionHandler) = CoroutineScope(
        Dispatchers.IO +
                SupervisorJob() +
                CoroutineName("IO_Scope") +
                handler
    )

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideApiService(gson: Gson, client: OkHttpClient): IMusicService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(IMusicService::class.java)
    }

    @Singleton
    @Provides
    fun provideDao(db:AppDatabase):PlayListDao{
        return db.getDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        scope: CoroutineScope,
        daoProvider : Provider<PlayListDao>
    ): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "app.db")
            .addCallback(object : RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    scope.launch {
                        initData(daoProvider.get())
                    }
                }
            })
            .build()
    }


    @Provides
    @Singleton
    fun providePlayer(
        @ApplicationContext context: Context
    ): MusicPlayer {
        return MusicPlayer().apply {
            setContext(context)
        }
    }

    @Provides
    @Singleton
    fun provideService() = MusicService()

    private suspend fun initData(dao:PlayListDao){
        val favorite = TrackListInfo(PlayListDao.ID_LIST_FAVORITE,"Favorite")
        val history = TrackListInfo(PlayListDao.ID_LIST_HISTORY,"History")
        dao.insertTrackList(favorite)
        dao.insertTrackList(history)
    }
}
