package com.example.taskweek2.model
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "LoginData")
data class LoginData (
    var query: String? = null,
    @field:SerializedName("fullName") val fullName :String?,
    @field:SerializedName("email") val email :String?,
    @field:SerializedName("username") val username :String?,
)
