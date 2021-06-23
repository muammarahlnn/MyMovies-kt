package com.ardnn.mymovies.models

import com.ardnn.mymovies.helpers.Utils
import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @SerializedName("last_air_date")
    val lastAirDate: String,

    @SerializedName("episode_run_time")
    val runtimes: List<String>,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("backdrop_path")
    val wallpaperUrl: String,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Utils.IMG_URL}${size.getValue()}${posterUrl}"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Utils.IMG_URL}${size.getValue()}${wallpaperUrl}"
    }
}