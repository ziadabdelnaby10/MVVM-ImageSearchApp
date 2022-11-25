package com.example.imagesearchapp.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


//this is for Image Search Fragment

@Parcelize
data class ImageSearch(
    val description: String? = "Sorry No Description available",
    val id: String,
    val likes: Int,
    val urls: Urls,
    val user: UserImage
) : Parcelable

@Parcelize
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Parcelable

@Parcelize
data class ImageSearchResponse(
    @Json(name = "results")
    val result: List<ImageSearch>
) : Parcelable

@Parcelize
data class UserImage(
    val name: String,
    val id: String,
    val username: String,
    @Json(name = "profile_image") val profileImage: ProfileImage,
    ) : Parcelable