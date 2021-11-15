package com.example.taskweek2.api

import com.example.taskweek2.model.LoginResponse
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
