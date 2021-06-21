package com.ardnn.mymovies.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TvShowsOutlineResponse (
    @SerializedName("results")
    @Expose
    val tvShowsOutlineList: List<TvShowsOutline>?
)
