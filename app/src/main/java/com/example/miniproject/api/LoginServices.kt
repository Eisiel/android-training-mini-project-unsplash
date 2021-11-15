package com.example.miniproject.api

import com.example.miniproject.model.LoginResponse
import retrofit2.http.*


interface LoginServices {
    @POST("api/user/login")
    @FormUrlEncoded
    suspend fun login(
        @Header("X-API-KEY") api : String,
        @Field("username") Username: String,
        @Field("password") password: String,
    ): LoginResponse
}
