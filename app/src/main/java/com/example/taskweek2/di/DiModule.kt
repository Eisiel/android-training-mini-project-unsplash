package com.example.taskweek2.di

import android.content.Context
import androidx.room.Room
import com.example.taskweek2.api.UnsplashService
import com.example.taskweek2.db.RepoDatabase
import com.example.taskweek2.ui.home.PhotoAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

//    @Provides
//    fun provideUnsplashService(): UnsplashService {
//        return UnsplashService.create()
//    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RepoDatabase {
        return Room.databaseBuilder(appContext,
            RepoDatabase::class.java, "Unsplash.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): UnsplashService {
        val BASE_URL = "https://api.unsplash.com/"

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashService::class.java)
    }

    @Provides
    fun providePhotoAdaptor(): PhotoAdapter {
        return PhotoAdapter()
    }

}