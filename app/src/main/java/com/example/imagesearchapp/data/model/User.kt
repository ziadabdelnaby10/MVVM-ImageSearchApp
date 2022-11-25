package com.example.imagesearchapp.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val bio: String?,
    val downloads: Int,
    val name: String,
    val id: String,
    val links: Links,
    val location: String?,
    @Json(name = "total_likes") val totalLikes: Int,
    @Json(name = "total_photos") val totalPhotos: Int,
    val username: String,
    @Json(name = "profile_image") val profileImage: ProfileImage,
    @Json(name = "followers_count") val followersCount: Int,
    @Json(name = "following_count") val followingCount: Int,
) : Parcelable {
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    val viewLocation get() = location ?: "Sorry There Is No Location Provided"
    val bioNotNull get() = bio ?: "Sorry No Bio Provided"
}

@Parcelize
data class Links(
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
) : Parcelable

@Parcelize
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Parcelable