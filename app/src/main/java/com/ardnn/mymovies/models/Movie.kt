package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("vote_average")
    val rating: Float?,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("backdrop_path")
    val wallpaperUrl: String,

    @SerializedName("genres")
    val genreList: List<Genre>
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Consts.IMG_URL}${size.getValue()}${posterUrl}"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Consts.IMG_URL}${size.getValue()}${wallpaperUrl}"
    }
}