package com.example.miniproject.model
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Login")
data class LoginResponse(
    var query: String? = null,
    @field:SerializedName("data") val data :LoginData,
    @field:SerializedName("message") val message :String,
    @field:SerializedName("status") val status :String,
    @field:SerializedName("token") val token :String,

)