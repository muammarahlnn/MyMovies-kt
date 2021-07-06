package com.ardnn.mymovies.models

import com.ardnn.mymovies.api.repositories.Consts
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieOutline(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterUrl: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("results")
    val movieOutlineList: MutableList<MovieOutline>,

    @SerializedName("cast")
    val personMovieList: MutableList<MovieOutline>
) {
    fun getPosterUrl(size: ImageSize) : String {
        return "${Consts.IMG_URL}${size.getValue()}${posterUrl}"
    }
}

