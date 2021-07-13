package com.ardnn.mymovies.api.callbacks

import com.ardnn.mymovies.models.MovieOutline

interface MovieOutlineCallback {
    fun onSuccess(movieOutlineList: MutableList<MovieOutline>)
    fun onFailure(message: String)
}