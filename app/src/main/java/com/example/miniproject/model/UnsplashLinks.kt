package com.example.miniproject.model

import com.google.gson.annotations.SerializedName

data class UnsplashLinks(
    @field:SerializedName("self") val self: String,
    @field:SerializedName("html") val html: String,
    @field:SerializedName("photos") val photos: String?,
    @field:SerializedName("likes") val likes: String?,
    @field:SerializedName("portfolio") val portfolio: String?,
    @field:SerializedName("download") val download: String?,
    @field:SerializedName("download_location") val download_location: String?
)
