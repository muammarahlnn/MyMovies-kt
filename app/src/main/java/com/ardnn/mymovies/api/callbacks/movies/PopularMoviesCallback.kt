package com.ardnn.mymovies.api.callbacks.movies

import com.ardnn.mymovies.models.MovieOutline

interface PopularMoviesCallback {
    fun onSuccess(popularMoviesList: MutableList<MovieOutline>)
    fun onFailure(message: String)
}