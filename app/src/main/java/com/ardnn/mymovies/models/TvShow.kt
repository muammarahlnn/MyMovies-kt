package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("last_air_date")
    val lastAirDate: String?,

    @SerializedName("episode_run_time")
    val runtimes: List<Int>?,

    @SerializedName("vote_average")
    val rating: Float?,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("backdrop_path")
    val wallpaperUrl: String,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,

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