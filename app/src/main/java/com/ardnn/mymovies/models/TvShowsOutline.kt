package com.ardnn.mymovies.models

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
    val rating: Double
)