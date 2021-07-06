package com.ardnn.mymovies.api.callbacks.person

import com.ardnn.mymovies.models.MovieOutline

interface PersonMoviesCallback {
    fun onSuccess(personMovieList: MutableList<MovieOutline>)
    fun onFailure(message: String)
}