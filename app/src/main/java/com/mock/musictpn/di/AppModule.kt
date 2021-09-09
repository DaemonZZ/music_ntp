package com.mock.musictpn.di

import android.util.Log
import com.mock.musictpn.network.IMusicService
import com.mock.musictpn.network.ApiContract.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHandler() = CoroutineExceptionHandler { context, throwable ->
        Log.e(
            "ThangDN6",
            "provideHandler: ${throwable.cause} \n " +
                    "scope: ${context[CoroutineName]}",
        )
        throwable.printStackTrace()
    }

    @Singleton
    @Provides
    fun provideIOScope(handler: CoroutineExceptionHandler) = CoroutineScope(
        Dispatchers.IO +
                SupervisorJob() +
                CoroutineName("main_scope") +
                handler
    )


    @Provides
    @Singleton
    fun provideSpiService(): IMusicService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(IMusicService::class.java)
    }
}