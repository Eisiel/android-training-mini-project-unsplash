/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.taskweek2.api

import com.example.taskweek2.model.UnsplashPhoto
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total") val total: Int = 0,
    @SerializedName("total_pages") val total_pages: Int = 0,
    @SerializedName("results") val results: List<UnsplashPhoto> = emptyList(),
    val nextPage: Int? = null
)