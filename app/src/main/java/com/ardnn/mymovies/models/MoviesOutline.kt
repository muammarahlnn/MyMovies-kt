package com.ardnn.mymovies.models

import com.ardnn.mymovies.helpers.Utils
import com.google.gson.annotations.SerializedName

data class MoviesOutline(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("vote_average")
    val rating: Double
) {
    fun getPosterUrl(size: ImageSize) : String {
        return "${Utils.IMG_URL}${size.getValue()}${posterUrl}"
    }
}

