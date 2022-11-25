package com.example.imagesearchapp.data.api

import com.example.imagesearchapp.data.model.Image
import com.example.imagesearchapp.data.model.ImageSearch
import com.example.imagesearchapp.data.model.ImageSearchResponse
import com.example.imagesearchapp.data.model.User
import com.example.imagesearchapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApiService {
    @Headers("Accept-Version: v1", "Authorization: Client-ID ${Constants.CLIENT_ID}")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") order_by : String = "latest"
    ): ImageSearchResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${Constants.CLIENT_ID}")
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : List<ImageSearch>

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${Constants.CLIENT_ID}")
    @GET("photos/{id}")
    suspend fun searchPhoto(
        @Path("id") id: String
    ) : Image

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${Constants.CLIENT_ID}")
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username : String
    ) : User

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${Constants.CLIENT_ID}")
    @GET("users/{username}/photos")
    suspend fun getUserPhotos(
        @Path("username") username : String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") order_by : String = "latest"
    ) : List<ImageSearch>

}