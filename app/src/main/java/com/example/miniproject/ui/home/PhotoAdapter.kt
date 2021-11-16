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

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.miniproject.R
import com.example.miniproject.model.UnsplashPhoto
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PhotoAdapter : PagingDataAdapter<UnsplashPhoto, PhotoViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        // item
        getItem(position)?.let { photo ->
            // image
            holder.itemView.setBackgroundColor(Color.parseColor(photo.color))
            holder.imageView.setOnClickListener {
                val action = UnsplashSearchFragmentDirections.actionUnsplashSearchFragmentToPhotoDetailFragment(photo.urls.regular, photo.user.name, photo.description)
                Navigation.findNavController(it).navigate(action)
            }

            Picasso.get().load(photo.urls.small)
                .into(holder.imageView)

            // photograph name
            holder.txtView.text = photo.user.name
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean =
                oldItem == newItem
        }
    }
}