package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.MovieOutline

interface TopRatedMoviesCallback {
    fun onSuccess(topRatedMoviesList: List<MovieOutline>)
    fun onFailure(message: String)
}