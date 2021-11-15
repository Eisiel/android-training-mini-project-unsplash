package com.example.miniproject.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class UnsplashPhoto(
//    @PrimaryKey(autoGenerate = true) val id: Long,
    var query: String? = null,
    @PrimaryKey @field:SerializedName("id") val id: String,
    @field:SerializedName("created_at") val created_at: String,
    @field:SerializedName("width") val width: Int,
    @field:SerializedName("height") val height: Int,
    @field:SerializedName("color") val color: String? = "#000000",
    @field:SerializedName("likes") val likes: Int,
    @field:SerializedName("description") val description: String?,
    @Embedded(prefix = "url_") @field:SerializedName("urls") val urls: UnsplashUrls,
    @Embedded(prefix = "link_") @field:SerializedName("links") val links: UnsplashLinks,
    @Embedded(prefix = "user_") @field:SerializedName("user") val user: UnsplashUser
)
