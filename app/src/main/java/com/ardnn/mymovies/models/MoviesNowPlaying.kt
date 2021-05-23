package com.ardnn.mymovies.models

import com.google.gson.annotations.SerializedName

data class MoviesNowPlaying(
    @SerializedName("title")
    var title: String,

    @SerializedName("release_date")
    var releaseDate: String,

    @SerializedName("overview")
    var synopsis: String,

    @SerializedName("poster_path")
    var posterUrl: String,

    @SerializedName("backdrop_path")
    var wallpaperUrl: String,

    @SerializedName("vote_average")
    var vote: String
)
