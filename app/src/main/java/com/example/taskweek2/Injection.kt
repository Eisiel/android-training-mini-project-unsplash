package com.example.taskweek2

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.taskweek2.api.UnsplashService
import com.example.taskweek2.db.RepoDatabase
import com.example.taskweek2.repository.UnsplashRepository
import com.example.taskweek2.ui.ViewModelFactory

//object Injection {
//    private fun provideUnsplashRepository(context: Context): UnsplashRepository {
//        return UnsplashRepository(UnsplashService.create(), RepoDatabase.getInstance(context))
//    }
//
//    fun provideViewModelFactory(context: Context, owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
//        return ViewModelFactory(owner, provideUnsplashRepository(context))
//    }
//
//}