package com.example.imagesearchapp.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

// This is for searching Specific Image With id

@Parcelize
data class Image(
    val description: String? = "Sorry No Description available",
    val id: String,
    val likes: Int,
    val downloads : Int,
    val urls: Urls,
    val user: ImageUser
) : Parcelable

@Parcelize
data class ImageUser(
    val name: String,
    val id: String,
    val username: String,
    val profile_image : ProfileImage
) : Parcelable{
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
}