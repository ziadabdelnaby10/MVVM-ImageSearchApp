package com.example.imagesearchapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearchapp.data.api.UnsplashApiService
import com.example.imagesearchapp.data.model.ImageSearch
import com.example.imagesearchapp.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class UnSplashPagingSource(
    private val unsplashApiService: UnsplashApiService,
    private val query: String
) : PagingSource<Int, ImageSearch>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageSearch> {
        val position = params.key ?: Constants.UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = unsplashApiService.searchPhotos(query, position, params.loadSize)
            val photos = response.result
            LoadResult.Page(
                data = photos,
                prevKey = if (position == Constants.UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageSearch>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}

class UnSplashPagingSourceUser(
    private val unsplashApiService: UnsplashApiService,
    private val query: String
) : PagingSource<Int, ImageSearch>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageSearch> {
        val position = params.key ?: Constants.UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = unsplashApiService.getUserPhotos(query, position, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (position == Constants.UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageSearch>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}

class UnSplashPagingSourceGeneralPhotos(
    private val unsplashApiService: UnsplashApiService
) : PagingSource<Int, ImageSearch>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageSearch> {
        val position = params.key ?: Constants.UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = unsplashApiService.getPhotos(position, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (position == Constants.UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageSearch>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}