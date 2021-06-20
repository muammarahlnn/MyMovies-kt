package com.ardnn.mymovies.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesOutlineResponse(
    @SerializedName("results")
    @Expose
    val moviesOutlineList: List<MoviesOutline>?
)
