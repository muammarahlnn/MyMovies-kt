package com.ardnn.mymovies.models

import com.ardnn.mymovies.helpers.Utils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TvShowsOutline(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val title: String,

    @SerializedName("first_air_date")
    val releaseDate: String,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("vote_average")
    val rating: Double,

    @SerializedName("results")
    val tvShowsOutlineList: List<TvShowsOutline>
) {
    fun getPosterUrl(size: ImageSize) : String {
        return "${Utils.IMG_URL}${size.getValue()}${posterUrl}"
    }
}