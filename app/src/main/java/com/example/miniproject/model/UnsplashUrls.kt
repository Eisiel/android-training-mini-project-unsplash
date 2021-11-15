package com.example.miniproject.model

import com.google.gson.annotations.SerializedName

data class UnsplashUrls(
    @field:SerializedName("thumb") val thumb: String?,
    @field:SerializedName("small") val small: String,
    @field:SerializedName("medium") val medium: String?,
    @field:SerializedName("regular") val regular: String?,
    @field:SerializedName("large") val large: String?,
    @field:SerializedName("full") val full: String?,
    @field:SerializedName("raw") val raw: String?
)
