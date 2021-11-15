package com.example.miniproject.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Unsplash API communication setup via Retrofit.
 */
interface UnsplashService {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") criteria: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("client_id") clientId: String = "dKVntOsousVcgceoCqSNOPUGHZ0D8CnOekgWsvsIJ0E",
    ): SearchResponse

//    companion object {
//        private const val BASE_URL = "https://api.unsplash.com/"
//
//        fun create(): UnsplashService {
//            val logger = HttpLoggingInterceptor()
//            logger.level = HttpLoggingInterceptor.Level.BASIC
//
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .build()
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(UnsplashService::class.java)
//        }
//    }
}
