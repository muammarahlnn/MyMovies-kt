package com.ardnn.mymovies.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var vote: Double
) : Parcelable
