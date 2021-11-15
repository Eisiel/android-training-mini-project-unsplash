package com.example.miniproject

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