package com.example.taskweek2.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.taskweek2.db.RepoDatabase
import com.example.taskweek2.api.UnsplashService
import com.example.taskweek2.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class UnsplashRepository @Inject constructor (
    private val service: UnsplashService,
    private val database: RepoDatabase
) {
    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory =  { database.reposDao().reposByName(dbQuery)}
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = UnsplashRemoteMediator(
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}