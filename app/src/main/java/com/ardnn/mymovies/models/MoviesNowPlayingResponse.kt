package com.ardnn.mymovies.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesNowPlayingResponse(
    @SerializedName("result")
    @Expose
    var moviesNowPlayingList: List<MoviesNowPlaying>
)
