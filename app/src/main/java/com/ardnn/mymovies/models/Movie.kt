package com.ardnn.mymovies.models

import com.ardnn.mymovies.helpers.Utils
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("runtime")
    val runtime: String,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("backdrop_path")
    val wallpaperUrl: String,
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Utils.IMG_URL}${size.getValue()}${posterUrl}"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Utils.IMG_URL}${size.getValue()}${wallpaperUrl}"
    }
}