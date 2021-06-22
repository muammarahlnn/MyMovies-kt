package com.ardnn.mymovies.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieOutlineResponse(
    @SerializedName("results")
    @Expose
    val movieOutlineList: List<MovieOutline>?
)
