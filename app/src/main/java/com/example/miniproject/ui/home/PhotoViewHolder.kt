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

package com.example.miniproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.R

class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    val imageView: ImageView = view.findViewById(R.id.item_photo_iv)
//    val imageView: ImageView = view.item_photo_iv
    val imageView: ImageView = view.findViewById(R.id.item_unsplash_photo_image_view)
    val txtView: TextView = view.findViewById(R.id.item_unsplash_photo_text_view)



    companion object {
        fun create(parent: ViewGroup): PhotoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
            return PhotoViewHolder(view)
        }
    }
}

//class RepoViewHolder(view: View):RecyclerView.ViewHolder(view) {
//    private val name: TextView = view.findViewById(R.id.repo_name)
//    private val description: TextView = view.findViewById(R.id.repo_description)
//    private val stars: TextView = view.findViewById(R.id.repo_stars)
//    private val language: TextView = view.findViewById(R.id.repo_language)
//    private val forks: TextView = view.findViewById(R.id.repo_forks)
//
//    private var photo: UnsplashPhoto? = null
//
//    init {
//        view.setOnClickListener{
//            photo?.url.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
//        }
//    }
//
//    fun bind(photo: UnsplashPhoto?){
//        if(photo == null){
//            val resources = itemView.resources
//            name.text = resources.getString(R.string.loading)
//            description.visibility = View.GONE
//            language.visibility = View.GONE
//            stars.text = resources.getString(R.string.unknown)
//            forks.text = resources.getString(R.string.unknown)
//        } else {
//            showRepoData(photo)
//        }
//    }
//
//    private fun showRepoData(photo: UnsplashPhoto){
//        this.photo = photo
//        name.text = photo.fullName
//
//        var descriptionVisibility = View.GONE
//        if (photo.description != null){
//            description.text = photo.description
//            descriptionVisibility = View.VISIBLE
//        }
//        description.visibility = descriptionVisibility
//
//        stars.text = photo.stars.toString()
//        forks.text = photo.forks.toString()
//
//        // if the language is missing, hide the label and the value
//        var languageVisibility = View.GONE
//        if (!photo.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, photo.language)
//            languageVisibility = View.VISIBLE
//        }
//        language.visibility = languageVisibility
//
//    }
//
//    companion object {
//        fun create(parent: ViewGroup): RepoViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.github_search_repository_item, parent, false)
//            return RepoViewHolder(view)
//        }
//    }
//}