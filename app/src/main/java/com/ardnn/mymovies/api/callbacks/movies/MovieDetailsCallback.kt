package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.Movie

interface MovieDetailsCallback {
    fun onSuccess(movie: Movie)
    fun onFailure(message: String)
}