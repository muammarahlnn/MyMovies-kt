package com.ardnn.mymovies.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesNowPlaying(
    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("overview")
    val synopsis: String,

    @SerializedName("poster_path")
    val posterUrl: String,

    @SerializedName("backdrop_path")
    val wallpaperUrl: String,

    @SerializedName("vote_average")
    val vote: Double
) : Parcelable
