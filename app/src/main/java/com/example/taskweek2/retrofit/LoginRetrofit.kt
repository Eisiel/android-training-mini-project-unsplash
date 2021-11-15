package com.example.taskweek2.retrofit

import com.example.taskweek2.api.UnsplashService
import com.example.taskweek2.model.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


//class LoginRetrofit {
//    private val loginApi: LoginService? = null
//
//    fun LoginRetrofit(): UnsplashService {
//        val BASE_URL = "https://talentpool.oneindonesia.id/"
//
//        val logger = HttpLoggingInterceptor()
//        logger.level = HttpLoggingInterceptor.Level.BASIC
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logger)
//            .build()
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(UnsplashService::class.java)
//    }
//
//    @GET("api/user/login")
//    suspend fun login(
//        @Header("Content-Type") Content :String = "application/x-www-form-urlencoded",
//        @Header("X-API-KEY") api : String = "454041184B0240FBA3AACD15A1F7A8BB",
//        @Query("username") criteria: String,
//        @Query("password") page: Int,
//    ): LoginResponse
//
//
//}