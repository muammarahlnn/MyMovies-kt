package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id")
    val id: Int = -1,

    @SerializedName("name")
    val title: String? = "",

    @SerializedName("overview")
    val overview: String? = "",

    @SerializedName("first_air_date")
    val firstAirDate: String? = "",

    @SerializedName("last_air_date")
    val lastAirDate: String? = "",

    @SerializedName("episode_run_time")
    val runtimes: List<Int>? = listOf(),

    @SerializedName("vote_average")
    val rating: Float? = -1F,

    @SerializedName("poster_path")
    val posterUrl: String = "",

    @SerializedName("backdrop_path")
    val wallpaperUrl: String = "",

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = -1,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = -1,

    @SerializedName("genres")
    val genreList: List<Genre> = listOf()
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Consts.IMG_URL}${size.getValue()}${posterUrl}"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Consts.IMG_URL}${size.getValue()}${wallpaperUrl}"
    }
}