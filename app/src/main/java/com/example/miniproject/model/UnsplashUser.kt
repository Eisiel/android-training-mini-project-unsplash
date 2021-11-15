package com.example.miniproject.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class UnsplashUser(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("username") val username: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("portfolio_url") val portfolio_url: String?,
    @field:SerializedName("bio") val bio: String?,
    @field:SerializedName("location") val location: String?,
    @field:SerializedName("total_likes") val total_likes: Int,
    @field:SerializedName("total_photos") val total_photos: Int,
    @field:SerializedName("total_collection") val total_collection: Int,
    @Embedded(prefix = "profile_image") @field:SerializedName("profile_image") val profile_image: UnsplashUrls?,
    @Embedded(prefix = "links") @field:SerializedName("links") val links: UnsplashLinks?
)
