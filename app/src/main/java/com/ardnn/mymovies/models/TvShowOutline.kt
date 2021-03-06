package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.SerializedName

data class TvShowOutline(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val title: String?,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterUrl: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("results")
    val tvShowOutlineList: MutableList<TvShowOutline>,

    @SerializedName("cast")
    val personTvShowList: MutableList<TvShowOutline>,
) {
    fun getPosterUrl(size: ImageSize) : String {
        return "${Consts.IMG_URL}${size.getValue()}${posterUrl}"
    }
}