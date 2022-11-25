package com.example.imagesearchapp.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imagesearchapp.data.api.UnsplashApiService
import com.example.imagesearchapp.data.paging.UnSplashPagingSource
import com.example.imagesearchapp.data.paging.UnSplashPagingSourceGeneralPhotos
import com.example.imagesearchapp.data.paging.UnSplashPagingSourceUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnSplashRepository @Inject constructor(private val unsplashApiService: UnsplashApiService) {
    fun getSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            UnSplashPagingSource(unsplashApiService, query)
        }
    ).liveData

    suspend fun getSearchPhoto(id: String) = unsplashApiService.searchPhoto(id)

    suspend fun getUserAsync(userName: String) = unsplashApiService.getUser(userName)

    fun getGeneralPhotos() = Pager(
        config = PagingConfig(
            pageSize = 5, maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            UnSplashPagingSourceGeneralPhotos(unsplashApiService)
        }
    ).flow

    fun getUserPhotos(userName: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            UnSplashPagingSourceUser(unsplashApiService, userName)
        }
    ).liveData
}